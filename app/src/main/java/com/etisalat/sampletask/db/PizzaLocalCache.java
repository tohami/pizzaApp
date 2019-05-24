package com.etisalat.sampletask.db;

import android.util.Log;

import com.etisalat.sampletask.api.PizzaApiResponse;
import com.etisalat.sampletask.model.PizzaResultModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;

public class PizzaLocalCache {
    //Constant used for Logs
    private static final String LOG_TAG = PizzaLocalCache.class.getSimpleName();

    //Dao for pizza Entity
    private PizzaDAO pizzaDAO;
    //Single Thread Executor for database operations
    private Executor ioExecutor;

    public PizzaLocalCache(PizzaDAO pizzaDAO, Executor ioExecutor) {
        this.pizzaDAO = pizzaDAO;
        this.ioExecutor = ioExecutor;
    }

    /**
     * Insert a list of pizza in the database, on a background thread.
     */
    public void insert(List<PizzaApiResponse.PizzaItem> pizzaItemList, InsertCallback callback) {
        ioExecutor.execute(() -> {
            Log.d(LOG_TAG, "insert: inserting " + pizzaItemList.size() + " pizza");
            pizzaDAO.insertPizza(pizzaItemList);

            TableMetaData pizzaTableMeta = new TableMetaData(
                    PizzaDatabase.PIZZA_TABLE_NAME ,
                    Calendar.getInstance().getTimeInMillis()) ;

            pizzaDAO.insertMeta(pizzaTableMeta);
            callback.insertFinished();
        });
    }

    /**
     * Request a pizza list from the Dao
     */
    public PizzaResultModel pizzaList() {
        List<PizzaApiResponse.PizzaItem> pizzaList = pizzaDAO.getPizzaItemList() ;
        Log.d(LOG_TAG, "read: reading " + pizzaList.size() + " pizza");
        TableMetaData metaData = pizzaDAO.getTableMeta(PizzaDatabase.PIZZA_TABLE_NAME) ;
        long lastUpdateAt = metaData != null ? metaData.lastUpdateTime : -1 ;
        Log.d(LOG_TAG, "read: reading " + lastUpdateAt + " pizzatable");
        return new PizzaResultModel(pizzaList , lastUpdateAt);
    }

    public interface InsertCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void insertFinished();
    }
}
