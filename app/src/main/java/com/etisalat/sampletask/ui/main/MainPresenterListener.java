package com.etisalat.sampletask.ui.main;

import com.etisalat.sampletask.bases.BasePresenterListener;
import com.etisalat.sampletask.model.PizzaResultModel;

public interface MainPresenterListener extends BasePresenterListener {
    void updatePizzaList(PizzaResultModel pizzaItemList) ;
    void displayError(String error) ;
    void updateProgressState(boolean isRequestInProgress);
}
