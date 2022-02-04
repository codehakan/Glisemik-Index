package com.hakanbey.glisemikindeks.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import java.lang.Exception

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 29.01.2022               │
//└──────────────────────────┘

class Database(
    val context: Context, name: String? = "GlisemikIndeks.db", factory: SQLiteDatabase.CursorFactory? = null, version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    private val categoryTableName: String = "Category"
    private val foodTableName: String = "Food"

    override fun onCreate(db: SQLiteDatabase?) {
        //  Create Category Table
        db?.execSQL(
            "CREATE TABLE \"$categoryTableName\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"name\"\tTEXT UNIQUE,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");"
        )

        //  Create Food Table
        db?.execSQL(
            "CREATE TABLE \"$foodTableName\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"index\"\tINTEGER,\n" +
                    "\t\"carbonHydrateIn100Gram\"\tREAL,\n" +
                    "\t\"calorieIn100Gram\"\tINTEGER,\n" +
                    "\t\"categoryId\"\tINTEGER,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");"
        )
        val service: HtmlService = HtmlService(context)
        service.getCategories()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $categoryTableName")
        db?.execSQL("DROP TABLE IF EXISTS $foodTableName")
        onCreate(db)
    }

    fun getCategories(): ArrayList<Category> {
        val categories: ArrayList<Category> = ArrayList()
        val read = this.readableDatabase
        val query: String = "SELECT * FROM $categoryTableName"
        val cursor: Cursor = read.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val category: Category = Category(id, name)
            categories.add(category)
        }
        cursor.close()
        return categories
    }

    fun addCategory(name: String): Long {
        val write = this.writableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put("name", name)
        val insertStatus: Long = write.insert(categoryTableName, null, contentValues)
        if (insertStatus > 0) {
            Log.e("addCategory", "$name added")
        } else {
            Log.e("addCategory", "$name failed")
        }
        return insertStatus
    }

    fun updateCategory(category: Category): Int {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", category.name)
        val result: Int = write.update(categoryTableName, contentValues, "id = ?", arrayOf(category.id.toString()))
        if (result > 0) {
            Log.e("updateCategory", "${category.name} updated")
        } else {
            Log.e("updateCategory", "${category.name} failed")
        }
        return result
    }

    fun deleteCategory(id: Int): Int {
        deleteFoodForCategoryId(id)
        val write = this.writableDatabase
        val result: Int = write.delete(categoryTableName, "id = ?", arrayOf(id.toString()))
        if (result > 0) {
            Log.e("deleteCategory", "$id deleted")
            return result
        } else {
            Log.e("deleteCategory", "$id failed")
        }
        return result
    }

    private fun deleteFoodForCategoryId(id: Int): Int {
        val write = this.writableDatabase
        val result: Int = write.delete(foodTableName, "categoryId = ?", arrayOf(id.toString()))
        if (result > 0) {
            Log.e("deleteCategory", "$id deleted")
        } else {
            Log.e("deleteCategory", "$id failed")
        }
        return result
    }

    fun addFood(food: Food): Long {
        val write = this.writableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put("name", food.name)
        contentValues.put("'index'", food.index)
        contentValues.put("carbonHydrateIn100Gram", food.carbonHydrateIn100Gram)
        contentValues.put("calorieIn100Gram", food.calorieIn100Gram)
        contentValues.put("categoryId", food.categoryId)
        val insertStatus: Long = write.insert(foodTableName, null, contentValues)
        if (insertStatus > 0) {
            Log.e("addFood", "${food.name} added")
        } else {
            Log.e("addFood", "${food.name} failed")
        }
        return insertStatus
    }

    fun getAllFoods(): ArrayList<Food> {
        val foodList: ArrayList<Food> = ArrayList()
        val read = this.readableDatabase
        val query: String = "SELECT * FROM $foodTableName"
        val cursor: Cursor = read.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(0)
            val name: String = cursor.getString(1)
            val index: Int = cursor.getInt(2)
            val carbonHydrateIn100Gram: Float = cursor.getFloat(3)
            val calorieIn100Gram: Int = cursor.getInt(4)
            val categoryId: Int = cursor.getInt(5)
            val food: Food = Food(id, name, index, carbonHydrateIn100Gram, calorieIn100Gram, categoryId)
            foodList.add(food)
        }


        cursor.close()
        return foodList
    }

    fun deleteFood(id: Int): Int {
        val write = this.writableDatabase
        val result: Int = write.delete(foodTableName, "id = ?", arrayOf(id.toString()))
        if (result > 0) {
            Log.e("deleteFood", "$id deleted")
        } else {
            Log.e("deleteFood", "$id failed")
        }
        return result
    }


    fun updateFood(food: Food): Int {
        val write = this.writableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put("name", food.name)
        contentValues.put("'index'", food.index)
        contentValues.put("carbonHydrateIn100Gram", food.carbonHydrateIn100Gram)
        contentValues.put("calorieIn100Gram", food.calorieIn100Gram)
        contentValues.put("categoryId", food.categoryId)
        val result: Int = write.update(foodTableName, contentValues, "id = ?", arrayOf(food.id.toString()))
        if (result > 0) {
            Log.e("updateFood", "${food.name} updated")
        } else {
            Log.e("updateFood", "${food.name} failed")
        }
        return result
    }

}