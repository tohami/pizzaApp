package com.etisalat.sampletask.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.etisalat.sampletask.api.PizzaServiceClient;
import com.etisalat.sampletask.db.PizzaDatabase;
import com.etisalat.sampletask.db.PizzaLocalCache;

import java.util.concurrent.Executors;


public class Injection {

    @NonNull
    private static PizzaLocalCache provideCache(Context context) {
        PizzaDatabase database = PizzaDatabase.getInstance(context);
        return new PizzaLocalCache(database.pizzaDAO(), Executors.newSingleThreadExecutor());
    }

    @NonNull
    public static RepositoryFactory provideRepositoryFactory(Context context) {
        return new RepositoryFactory(PizzaServiceClient.create(), provideCache(context));
    }
}
