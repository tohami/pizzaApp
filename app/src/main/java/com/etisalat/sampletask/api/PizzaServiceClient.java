package com.etisalat.sampletask.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class PizzaServiceClient {
    private static final String LOG_TAG = PizzaServiceClient.class.getSimpleName();

    private static final String BASE_URL = "https://api.androidhive.info/";

    /**
     * Creates the Retrofit Service for Github API
     *
     * @return The {@link PizzaApiService} instance
     */
    public static PizzaApiService create() {
        //Initializing HttpLoggingInterceptor to receive the HTTP event logs
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        //Building the HTTPClient with the logger
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        //Returning the Retrofit service for the BASE_URL
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //Using the HTTPClient setup
                .client(httpClient)
                //XML converter to convert the XML elements to a POJO
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                //Creating the service for the defined API Interface
                .create(PizzaApiService.class);
    }


    public static void getPizzaList(PizzaApiService service, final ApiCallback<List<PizzaApiResponse.PizzaItem>> apiCallback) {

        //Executing the API asynchronously
        service.getPizza().enqueue(new Callback<PizzaApiResponse>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<PizzaApiResponse> call, @NonNull Response<PizzaApiResponse> response) {
                Log.d(LOG_TAG, "onResponse: Got response: " + response);
                if (response.isSuccessful()) {
                    //Retrieving the pizza Items when the response is successful
                    List<PizzaApiResponse.PizzaItem> pizzaItemList;
                    if (response.body() != null) {
                        pizzaItemList = response.body().getPizzaItemList();
                    } else {
                        pizzaItemList = new ArrayList<>();
                    }
                    //Pass the result to the callback
                    apiCallback.onSuccess(pizzaItemList);
                } else {
                    //When the response is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "onResponse: Failed while reading errorBody: ", e);
                    }
                }
            }

            //Called on Failure of the request
            @Override
            public void onFailure(@Nullable Call<PizzaApiResponse> call, @NonNull Throwable t) {
                Log.d(LOG_TAG, "onFailure: Failed to get data");
                //Pass the error to the callback
                apiCallback.onError(t.getMessage() != null ?
                        t.getMessage() : "Unknown error");
            }
        });
    }

    public interface ApiCallback<T> {

        void onSuccess(T items);

        void onError(String errorMessage);
    }
}
