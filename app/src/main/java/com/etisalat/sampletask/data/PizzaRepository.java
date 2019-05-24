package com.etisalat.sampletask.data;

import android.util.Log;

import com.etisalat.sampletask.api.PizzaApiResponse;
import com.etisalat.sampletask.api.PizzaApiService;
import com.etisalat.sampletask.api.PizzaServiceClient;
import com.etisalat.sampletask.bases.BaseRepository;
import com.etisalat.sampletask.db.PizzaLocalCache;
import com.etisalat.sampletask.model.PizzaResultModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.subjects.PublishSubject;

public class PizzaRepository extends BaseRepository
        implements PizzaServiceClient.ApiCallback<List<PizzaApiResponse.PizzaItem>> {

    private final ExecutorService executer;
    private PublishSubject<PizzaResultModel> pizzaSubject ;

    public PizzaRepository(PizzaApiService apiService, PizzaLocalCache localCache) {
        super(apiService, localCache);
        pizzaSubject = PublishSubject.create() ;
        this.executer = Executors.newSingleThreadExecutor() ;
    }

    public PublishSubject<PizzaResultModel> getPizzaSubject() {
        if(pizzaSubject.hasComplete() || pizzaSubject.hasThrowable())
            pizzaSubject = PublishSubject.create() ;

        return pizzaSubject;
    }


    public void getPizza() {
        updatePizzaList(false);
        getPizzaFromApi();
    }

    private void updatePizzaList(boolean fromApi) {
        executer.execute(() -> {
            Log.d("Repo", "do on next");
            pizzaSubject.onNext(getPizzaFromCache());
            if(fromApi)
                pizzaSubject.onComplete();
        });
    }
    /**
     * get all pizza from cache.
     */
    private PizzaResultModel getPizzaFromCache() {
        return localCache.pizzaList();
    }

    private void getPizzaFromApi() {
        PizzaServiceClient.getPizzaList(apiService , this);
    }

    @Override
    public void onSuccess(List<PizzaApiResponse.PizzaItem> items) {
        localCache.insert(items, () -> updatePizzaList(true));
    }

    @Override
    public void onError(String errorMessage) {
        pizzaSubject.onError(new Throwable(errorMessage));
    }
}
