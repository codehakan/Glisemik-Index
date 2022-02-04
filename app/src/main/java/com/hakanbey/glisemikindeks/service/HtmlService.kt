package com.hakanbey.glisemikindeks.service

import android.content.Context
import android.util.Log
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.util.Util
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 29.01.2022               │
//└──────────────────────────┘

class HtmlService(context: Context) {

    private var database: Database = Database(context)

    private var sData: String = ""
    private lateinit var document: Document

    fun getCategories() {
        val threadCategory: Thread = Thread {
            Log.e("TAG", "ERROR getCategories: Kategori Thread Başladı")
            //  Get Data
            sData = Jsoup.connect(Util.HTML_DATA_URL).ignoreContentType(true).timeout(15000).get().toString()

            //  Parse
            document = Jsoup.parse(sData, "UTF-8", Parser.htmlParser())

            //  Category Elements
            val categoryElements: Elements = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(n) > tbody > tr:nth-child(1) > td > p > font > font > b")

            //  Categories Add in Database
            for (element in categoryElements) {
                val name: String = element.text()
                database.addCategory(name)
                Log.e("TAG", "getCategories: $name")
            }

            Log.e("TAG", "ERROR getCategories: Kategori Thread Bitti")
        }

        val threadFoodOne: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryOne: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(3) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(3) > tbody > tr:nth-child($i) > td > p > font > font > b")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = result[3].text().toString().toInt()
                    val foodCategoryId: Int = 1
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: ${food.toString()}")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryOne: Food Thread Bitti")
        }

        val threadFoodTwo: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryTwo: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(5) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(5) > tbody > tr:nth-child($i) > td > p")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = if (result[2].text().toString().isEmpty()) 0.0f else result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = if (result[3].text().toString().isEmpty()) 0 else result[3].text().toString().toInt()
                    val foodCategoryId: Int = 2
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: $food")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryTwo: Food Thread Bitti")
        }

        val threadFoodThree: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(7) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(7) > tbody > tr:nth-child($i) > td > p > font > font > b")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = result[3].text().toString().toInt()
                    val foodCategoryId: Int = 3
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: ${food.toString()}")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Bitti")
        }

        val threadFoodFour: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(9) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(9) > tbody > tr:nth-child($i) > td > p > font > font > b")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = result[3].text().toString().toInt()
                    val foodCategoryId: Int = 4
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: ${food.toString()}")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Bitti")
        }

        val threadFoodFive: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(11) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(11) > tbody > tr:nth-child($i) > td > p > font > font > b")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = result[3].text().toString().toInt()
                    val foodCategoryId: Int = 5
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: ${food.toString()}")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Bitti")
        }

        val threadFoodSix: Thread = Thread {
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Başladı")
            //  Food Elements - Category 1
            val sebzeMeyveCount: Int = (document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(13) > tbody > tr:nth-child(n) > td > p > font > font > b").size)

            for (i in 3..sebzeMeyveCount) {
                val result = document.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(13) > tbody > tr:nth-child($i) > td > p > font > font > b")
                if (result.size > 0) {
                    val foodName: String = result[0].text()
                    val foodIndex: Int = result[1].text().toString().toInt()
                    val foodCarbonHydrateIn100Gram: Float = result[2].text().toString().replace(',', '.').toFloat()
                    val foodCalorieIn100Gram: Int = result[3].text().toString().toInt()
                    val foodCategoryId: Int = 6
                    val food: Food = Food()
                    food.name = foodName
                    food.index = foodIndex
                    food.carbonHydrateIn100Gram = foodCarbonHydrateIn100Gram
                    food.calorieIn100Gram = foodCalorieIn100Gram
                    food.categoryId = foodCategoryId
                    database.addFood(food)
                    Log.e("TAG", "Food: ${food.toString()}")
                }
            }
            Log.e("TAG", "ERROR threadFoodCategoryThree: Food Thread Bitti")
            Util.dataStatus.postValue(true)
        }

        val runnable = Runnable {
            threadCategory.start()
            threadCategory.join()

            threadFoodOne.start()
            threadFoodOne.join()

            threadFoodTwo.start()
            threadFoodTwo.join()

            threadFoodThree.start()
            threadFoodThree.join()

            threadFoodFour.start()
            threadFoodFour.join()

            threadFoodFive.start()
            threadFoodFive.join()

            threadFoodSix.start()
            threadFoodSix.join()
        }

        Thread(runnable).start()

    }

}