package com.hakanbey.glisemikindeks.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.databinding.SingleFoodBinding
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.util.Util

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 30.01.2022               │
//└──────────────────────────┘

class FoodAdapter(private val foodList: ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {
    private val foodListBackup: ArrayList<Food> = ArrayList(foodList)
    private val searchingList: ArrayList<Food> = ArrayList(foodList)

    class MyViewHolder(val binding: SingleFoodBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SingleFoodBinding = SingleFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food: Food = searchingList[position]

        holder.binding.apply {

            if (food.index!! < Util.GLISEMIK_INDEX_LOW_MAX) {
                //  Düşük
                singleFoodCvIndex.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.index_low))
            } else if (food.index!! < Util.GLISEMIK_INDEX_MEDIUM_MAX) {
                //  Orta
                singleFoodCvIndex.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.index_medium))
            } else {
                //  Yüksek
                singleFoodCvIndex.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.index_high))
            }

            //  Besin Index
            singleFoodLblIndex.text = food.index.toString()

            //  Besin Adı
            singleFoodLblFood.text = food.name

            //  Satıra Tıklandığında
            singleFoodCvContainer.setOnClickListener {
                Util.foodIdForDetail = food.id!!
                it.findNavController().navigate(R.id.action_mainFragment_to_foodDetailFragment)
            }

            //  Animasyon
            val animation: Animation = AnimationUtils.loadAnimation(root.context, R.anim.anim_recyclerview_food)
            singleFoodCvContainer.startAnimation(animation)
        }
    }

    fun searchFood(query: String) {
        searchingList.clear()
        for (item in foodListBackup) {
            if (item.name!!.lowercase().contains(query.lowercase())
                || item.index!!.toString().contains(query.lowercase())
                || item.carbonHydrateIn100Gram!!.toString().contains(query.lowercase())
                || item.calorieIn100Gram!!.toString().contains(query.lowercase())
            ) {
                searchingList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun foodFilter(categoryId: Int) {
        searchingList.clear()
        if (categoryId == -1) {
            searchingList.addAll(foodListBackup)
        } else {
            for (item in foodListBackup) {
                if (item.categoryId == categoryId) {
                    searchingList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return searchingList.count()
    }
}