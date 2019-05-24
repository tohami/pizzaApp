package com.etisalat.sampletask.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = PizzaDatabase.METADATA_TABLE_NAME)
public class TableMetaData {
    @PrimaryKey
    @NotNull
    String tableName;

    long lastUpdateTime ;

    TableMetaData(@NotNull String tableName, long lastUpdateTime) {
        this.tableName = tableName;
        this.lastUpdateTime = lastUpdateTime;
    }

    @NotNull
    String getTableName() {
        return tableName;
    }

    long getLastUpdateTime() {
        return lastUpdateTime;
    }

}
