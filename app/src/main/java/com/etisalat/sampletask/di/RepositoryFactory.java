package com.etisalat.sampletask.di;

import com.etisalat.sampletask.api.PizzaApiService;
import com.etisalat.sampletask.bases.BaseRepository;
import com.etisalat.sampletask.db.PizzaLocalCache;

import java.lang.reflect.InvocationTargetException;

public class RepositoryFactory {
    private PizzaApiService apiService;
    private PizzaLocalCache localCache;

    RepositoryFactory(PizzaApiService apiService, PizzaLocalCache localCache) {
        this.apiService = apiService;
        this.localCache = localCache;
    }

    public <T extends BaseRepository> T create(Class<T> repositoryClass) {
        if (BaseRepository.class.isAssignableFrom(repositoryClass)) {
            try {
                return repositoryClass.getConstructor(PizzaApiService.class, PizzaLocalCache.class).newInstance(apiService, localCache);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown Reposotiry class " + repositoryClass);

    }
}
