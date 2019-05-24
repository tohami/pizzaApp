package com.etisalat.sampletask.bases;

import com.etisalat.sampletask.api.PizzaApiService;
import com.etisalat.sampletask.db.PizzaLocalCache;

public class BaseRepository {
    protected final PizzaApiService apiService;
    protected final PizzaLocalCache localCache;

    public BaseRepository(PizzaApiService apiService, PizzaLocalCache localCache) {
        this.apiService = apiService;
        this.localCache = localCache;
    }
}
