package com.etisalat.sampletask.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.etisalat.sampletask.api.PizzaApiResponse;

@Database(entities = {PizzaApiResponse.PizzaItem.class , TableMetaData.class}, version = 1, exportSchema = false)
public abstract class PizzaDatabase extends RoomDatabase {

    private static volatile PizzaDatabase INSTANCE;
    public static final String PIZZA_TABLE_NAME = "pizza" ;
    static final String METADATA_TABLE_NAME = "table_info" ;

    public static PizzaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PizzaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static PizzaDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                PizzaDatabase.class,
                "Pizza.db"
        ).build();
    }

    public abstract PizzaDAO pizzaDAO();
}
