package com.etisalat.sampletask

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.etisalat.sampletask.api.PizzaApiResponse
import com.etisalat.sampletask.db.PizzaDAO
import com.etisalat.sampletask.db.PizzaDatabase
import com.etisalat.sampletask.db.PizzaLocalCache
import com.etisalat.sampletask.di.Injection
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var pizzaDAO: PizzaDAO

    private lateinit var pizzaDb: PizzaDatabase


    @Before
    fun createDb() {
        pizzaDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(), PizzaDatabase::class.java).build()
        pizzaDAO = pizzaDb.pizzaDAO()
    }



    @After
    @Throws(IOException::class)
    fun closeDb() {
        pizzaDb.close()
    }


    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val pizzaList = createTestPizza()

        pizzaDAO.insertPizza(pizzaList)

        val pizzaFromDb = pizzaDAO.pizzaItemList
        //check that the duplicated id item not inserted
        assertThat(pizzaFromDb.size, equalTo(5))
        //check that the item is orderd by name
        assertThat(pizzaFromDb.get(0).name, equalTo("pizza item"))
        assertThat(pizzaFromDb.get(4).name, equalTo("pizza item 4"))
    }



    fun createTestPizza(): ArrayList<PizzaApiResponse.PizzaItem> {
        var pizzaList = ArrayList<PizzaApiResponse.PizzaItem>()
        var pizzaItem = PizzaApiResponse.PizzaItem()
        pizzaItem.id = "1"
        pizzaItem.name = "pizza item"
        pizzaItem.cost = "5"
        pizzaItem.description = "description"

        pizzaList.add(pizzaItem)

        var pizzaItem2 = PizzaApiResponse.PizzaItem()
        pizzaItem2.id = "2"
        pizzaItem2.name = "pizza item 2"
        pizzaItem2.cost = "5"
        pizzaItem2.description = "description 2"

        pizzaList.add(pizzaItem2)

        var pizzaItem3 = PizzaApiResponse.PizzaItem()
        pizzaItem3.id = "3"
        pizzaItem3.name = "pizza item 3"
        pizzaItem3.description = "description 3"

        pizzaList.add(pizzaItem3)

        var pizzaItem4 = PizzaApiResponse.PizzaItem()
        pizzaItem4.id = "4"
        pizzaItem4.name = "pizza item 4"
        pizzaItem4.cost = "5"

        pizzaList.add(pizzaItem4)

        var pizzaItem5 = PizzaApiResponse.PizzaItem()
        pizzaItem5.id = "5"
        pizzaItem5.name = "pizza item 1"
        pizzaItem5.cost = "5"

        pizzaList.add(pizzaItem5)


        var pizzaItem6 = PizzaApiResponse.PizzaItem()
        pizzaItem6.id = "5"
        pizzaItem6.name = "pizza item 1"
        pizzaItem6.cost = "5"

        pizzaList.add(pizzaItem6)
        return pizzaList
    }
}