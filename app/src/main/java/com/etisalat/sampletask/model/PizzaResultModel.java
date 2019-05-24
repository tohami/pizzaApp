package com.etisalat.sampletask.model;

import com.etisalat.sampletask.api.PizzaApiResponse;

import java.util.ArrayList;
import java.util.List;

public class PizzaResultModel {
    private final List<PizzaApiResponse.PizzaItem> pizzaItemList ;
    public final long createAt;

    public PizzaResultModel(List<PizzaApiResponse.PizzaItem> pizzaItemList,
                            long createAt) {
        this.pizzaItemList = pizzaItemList;
        this.createAt = createAt ;
    }

    public List<PizzaApiResponse.PizzaItem> getPizzaItemList() {
        return new ArrayList<>(pizzaItemList);
    }

    
}
