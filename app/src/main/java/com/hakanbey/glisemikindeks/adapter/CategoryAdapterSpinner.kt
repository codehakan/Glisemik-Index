package com.hakanbey.glisemikindeks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hakanbey.glisemikindeks.databinding.SingleCategoryBinding
import com.hakanbey.glisemikindeks.model.Category

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 3.02.2022               │
//└──────────────────────────┘

class CategoryAdapterSpinner(
    private val categories: ArrayList<Category>
) : BaseAdapter() {
    override fun getCount(): Int {
        return categories.count()
    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: SingleCategoryBinding = if (convertView == null) {
            SingleCategoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            SingleCategoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        }

        //  Current Item
        val category: Category = categories[position]

        //  Set Category Title
        binding.singleCategoryLblTitle.text = category.name

        //  Hide Delete & Edit Button
        binding.singleCategoryBtnDelete.visibility = View.GONE
        binding.singleCategoryBtnEdit.visibility = View.GONE

        return binding.root
    }
}