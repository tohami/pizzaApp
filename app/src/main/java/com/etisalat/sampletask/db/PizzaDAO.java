package com.etisalat.sampletask.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.etisalat.sampletask.api.PizzaApiResponse;

import java.util.List;

@Dao
public interface PizzaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPizza(List<PizzaApiResponse.PizzaItem> pizzaItemList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeta(TableMetaData metaData);

    @Query("SELECT * FROM pizza ORDER BY name ASC")
    List<PizzaApiResponse.PizzaItem> getPizzaItemList();

    @Query("SELECT * FROM table_info WHERE tableName = :tableName LIMIT 1")
    TableMetaData getTableMeta(String tableName);

}
