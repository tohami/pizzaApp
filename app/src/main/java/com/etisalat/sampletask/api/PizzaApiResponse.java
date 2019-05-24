package com.etisalat.sampletask.api;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.etisalat.sampletask.db.PizzaDatabase;

import org.jetbrains.annotations.NotNull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "menu", strict = false)
public class PizzaApiResponse {

    @ElementList(inline = true)
    private List<PizzaItem> pizzaItemList;

    List<PizzaItem> getPizzaItemList() {
        return pizzaItemList;
    }

    public void setPizzaItemList(List<PizzaItem> pizzaItemList) {
        this.pizzaItemList = pizzaItemList;
    }

    @Root(name = "item")
    @Entity(tableName = PizzaDatabase.PIZZA_TABLE_NAME)

    public static class PizzaItem {
        @Element(name = "id")
        @PrimaryKey
        @NotNull
        private String id;
        @Element(name = "name")
        private String name;
        @Element(name = "cost")
        private String cost;
        @Element(name = "description")
        private String description;

        @NotNull
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
