package com.etisalat.sampletask.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PizzaApiService {

    @GET("/pizza/?format=xml")
    Call<PizzaApiResponse> getPizza();

}