package com.etisalat.sampletask.ui.main;

import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.data.PizzaRepository;
import com.etisalat.sampletask.di.RepositoryFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter extends BasePresenter<MainPresenterListener> {

    private final PizzaRepository pizzaRepository;

    private final CompositeDisposable compositeDisposable;

    MainPresenter(MainPresenterListener listener, RepositoryFactory repoFactory) {
        super(listener);
        this.pizzaRepository = repoFactory.create(PizzaRepository.class);
        this.compositeDisposable = new CompositeDisposable();
    }

    void refreshPizzaList() {
        listener.updateProgressState(true);
        compositeDisposable.add(
                pizzaRepository
                        .getPizzaSubject()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pizzaResultModel -> listener.updatePizzaList(pizzaResultModel),
                                throwable -> {
                                    listener.updateProgressState(false);
                                    listener.displayError(throwable.getMessage());
                                },
                                () -> listener.updateProgressState(false))
        );

        pizzaRepository.getPizza();
    }

    public void deAttach() {
        compositeDisposable.dispose();
    }
}
