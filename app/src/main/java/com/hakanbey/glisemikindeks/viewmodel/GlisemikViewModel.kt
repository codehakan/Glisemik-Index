package com.hakanbey.glisemikindeks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.service.Database
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 3.02.2022               │
//└──────────────────────────┘

class GlisemikViewModel @Inject constructor(
    private val database: Database
) : ViewModel() {

    val foodList: MutableLiveData<ArrayList<Food>> = MutableLiveData()
    val categories: MutableLiveData<ArrayList<Category>> = MutableLiveData()


    fun addCategory(name: String): Long {
        return database.addCategory(name)
    }

    fun addFood(food: Food): Long {
        return database.addFood(food)
    }

    fun updateFood(food: Food): Int {
        return database.updateFood(food)
    }

    fun deleteFood(id: Int): Int {
        return database.deleteFood(id)
    }

    fun refreshFoodList() {
        this.foodList.postValue(database.getAllFoods())
    }

    fun refreshCategories() {
        this.categories.postValue(database.getCategories())
    }

}