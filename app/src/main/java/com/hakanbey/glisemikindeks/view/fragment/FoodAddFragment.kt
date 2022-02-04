package com.hakanbey.glisemikindeks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterSpinner
import com.hakanbey.glisemikindeks.databinding.FragmentFoodAddBinding
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.viewmodel.GlisemikViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 03.02.2022               │
//└──────────────────────────┘

@AndroidEntryPoint
class FoodAddFragment
@Inject constructor(
    private val viewModel: GlisemikViewModel
) : Fragment(R.layout.fragment_food_add) {
    //  Binding
    private lateinit var binding: FragmentFoodAddBinding

    //  Category Adapter
    private lateinit var categoryAdapterSpinner: CategoryAdapterSpinner


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFoodAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Category Adapter
        viewModel.categories.value?.let {
            categoryAdapterSpinner = CategoryAdapterSpinner(it)
            binding.foodAddSpinCategory.adapter = categoryAdapterSpinner
        }

        //  Listener
        objectListener()
    }

    private fun objectListener() {
        //  Geri Butonu
        binding.incToolbarFoodAdd.toolbarFoodAddToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
            requireActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

        var cagetoryId: Int = 0
        //  Spinner
        binding.foodAddSpinCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cagetoryId = (parent!!.getItemAtPosition(position) as Category).id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //  Ekle Butonu
        binding.foodAddBtn.setOnClickListener {
            if (binding.foodAddTxtName.text.toString().isEmpty()) {
                Snackbar.make(binding.root, "Lütfen besin adını boş bırakmayın.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                    .show()
            } else if (binding.foodAddTxtIndex.text.toString().isEmpty()) {
                Snackbar.make(binding.root, "Lütfen indeks değerini boş bırakmayın.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                    .show()
            } else if (binding.foodAddTxtCarbonHydrateIn100Gram.text.toString().isEmpty()) {
                Snackbar.make(binding.root, "Lütfen karbonhidrat değerini boş bırakmayın.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                    .show()
            } else if (binding.foodAddTxtCalorieIn100Gram.text.toString().isEmpty()) {
                Snackbar.make(binding.root, "Lütfen kalori değerini boş bırakmayın.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                    .show()
            } else {
                val name: String = binding.foodAddTxtName.text.toString()
                val index: Int = binding.foodAddTxtIndex.text.toString().toInt()
                val carbonHydrateIn100Gram: Float = binding.foodAddTxtCarbonHydrateIn100Gram.text.toString().toFloat()
                val calorieIn100Gram: Int = binding.foodAddTxtCalorieIn100Gram.text.toString().toInt()
                val categoryId: Int = cagetoryId
                val food: Food = Food()
                food.name = name
                food.index = index
                food.carbonHydrateIn100Gram = carbonHydrateIn100Gram
                food.calorieIn100Gram = calorieIn100Gram
                food.categoryId = categoryId
                addFood(food)
            }
        }
    }

    private fun addFood(food: Food) {
        val result: Long = viewModel.addFood(food)
        if (result > 0) {
            viewModel.refreshFoodList()
            Snackbar.make(binding.root, "Besin başarıyla eklendi.", Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(R.color.index_low, requireContext().theme))
                .show()
            findNavController().popBackStack()
        } else {
            Snackbar.make(binding.root, "Besin ekleme başarısız!", Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                .show()
        }
    }
}