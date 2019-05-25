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

/**
 * pizza repo that manage data access
 * support the presenter with data either from db or api
 */
public class PizzaRepository extends BaseRepository
        implements PizzaServiceClient.ApiCallback<List<PizzaApiResponse.PizzaItem>> {

    private final ExecutorService executer;
    private PublishSubject<PizzaResultModel> pizzaSubject ;

    public PizzaRepository(PizzaApiService apiService, PizzaLocalCache localCache) {
        super(apiService, localCache);
        pizzaSubject = PublishSubject.create() ;
        this.executer = Executors.newSingleThreadExecutor() ;
    }

    /**
     * return the already existing pizza rx subject or new subject if the existing completed
     * or has error
     */
    public PublishSubject<PizzaResultModel> getPizzaSubject() {
        if(pizzaSubject.hasComplete() || pizzaSubject.hasThrowable())
            pizzaSubject = PublishSubject.create() ;
        return pizzaSubject;
    }


    /**
     * return the cached version of data
     */
    public void getCachedPizza() {
        updatePizzaList(false);
    }

    /**
     * push the cached version of pizza in the subject to be cached by presenter
     * always relay on cached version
     * check the readme to figure out how it work
     * @param fromApi
     */
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

    /**
     * get pizza from api with ApiCallback contract
     */
    public void getPizzaFromApi() {
        PizzaServiceClient.getPizzaList(apiService , this);
    }

    /**
     * when api successfull return data , insert it into cache then
     * push the new cache into the rx subject
     * @param items
     */
    @Override
    public void onSuccess(List<PizzaApiResponse.PizzaItem> items) {
        localCache.insert(items, () -> updatePizzaList(true));
    }

    /**
     * forward the error to the presenter using subject
     * @param errorMessage
     */
    @Override
    public void onError(String errorMessage) {
        pizzaSubject.onError(new Throwable(errorMessage));
    }
}
