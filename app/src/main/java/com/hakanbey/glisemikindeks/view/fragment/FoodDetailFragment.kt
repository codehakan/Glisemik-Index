package com.hakanbey.glisemikindeks.view.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterSpinner
import com.hakanbey.glisemikindeks.databinding.DialogFoodEditBinding
import com.hakanbey.glisemikindeks.databinding.DialogInformationBinding
import com.hakanbey.glisemikindeks.databinding.FragmentFoodDetailBinding
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.util.Util
import com.hakanbey.glisemikindeks.util.Util.GLISEMIK_INDEX_MEDIUM_MAX
import com.hakanbey.glisemikindeks.util.Util.foodIdForDetail
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
class FoodDetailFragment
@Inject constructor(
    private val viewModel: GlisemikViewModel
) : Fragment(R.layout.fragment_food_detail) {
    //  Binding
    private lateinit var binding: FragmentFoodDetailBinding

    //  Current Item
    private lateinit var food: Food

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Object Listener
        objectListener()

        //  Load Food Details
        loadDetails()
    }

    private fun loadDetails() {
        Log.d("TAG", "loadDetails: ")
        viewModel.foodList.observe(this, {
            it?.let {
                //food = it[Util.foodPosition]
                it.forEachIndexed { _, food ->
                    if (foodIdForDetail == food.id) {
                        this.food = food
                    }
                }
                //  Besin Adı
                binding.foodDetailLblName.text = food.name

                //  Besin Index
                binding.foodDetailLblIndex.text = food.index.toString()
                binding.foodDetailProgressIndex.progress = food.index!!
                if (food.index!! < Util.GLISEMIK_INDEX_LOW_MAX) {
                    binding.foodDetailProgressIndex.setIndicatorColor(resources.getColor(R.color.index_low, requireContext().theme))
                } else if (food.index!! < Util.GLISEMIK_INDEX_MEDIUM_MAX) {
                    binding.foodDetailProgressIndex.setIndicatorColor(resources.getColor(R.color.index_medium, requireContext().theme))
                } else {
                    binding.foodDetailProgressIndex.setIndicatorColor(resources.getColor(R.color.index_high, requireContext().theme))
                }

                //  CarbonHydrateIn100Gram
                binding.foodDetailLblCarbonHydrateIn100Gram.text = food.carbonHydrateIn100Gram.toString()
                binding.foodDetailProgressCarbonHydrateIn100Gram.progress = food.carbonHydrateIn100Gram!!.toInt()
                if (food.carbonHydrateIn100Gram!! < Util.GLISEMIK_INDEX_LOW_MAX) {
                    binding.foodDetailProgressCarbonHydrateIn100Gram.setIndicatorColor(resources.getColor(R.color.index_low, requireContext().theme))
                } else if (food.carbonHydrateIn100Gram!! < GLISEMIK_INDEX_MEDIUM_MAX) {
                    binding.foodDetailProgressCarbonHydrateIn100Gram.setIndicatorColor(resources.getColor(R.color.index_medium, requireContext().theme))
                } else {
                    binding.foodDetailProgressCarbonHydrateIn100Gram.setIndicatorColor(resources.getColor(R.color.index_high, requireContext().theme))
                }

                //  calorieIn100Gram
                binding.foodDetailLblCalorieIn100Gram.text = food.calorieIn100Gram.toString()
                binding.foodDetailProgressCalorieIn100Gram.progress = food.calorieIn100Gram!!.toInt()
                if (food.calorieIn100Gram!! < Util.GLISEMIK_INDEX_LOW_MAX) {
                    binding.foodDetailProgressCalorieIn100Gram.setIndicatorColor(resources.getColor(R.color.index_low, requireContext().theme))
                } else if (food.calorieIn100Gram!! < GLISEMIK_INDEX_MEDIUM_MAX) {
                    binding.foodDetailProgressCalorieIn100Gram.setIndicatorColor(resources.getColor(R.color.index_medium, requireContext().theme))
                } else {
                    binding.foodDetailProgressCalorieIn100Gram.setIndicatorColor(resources.getColor(R.color.index_high, requireContext().theme))
                }

                //  Kategori
                viewModel.categories.value?.forEach { cat ->
                    Log.d("TAG", "loadDetails: ${cat.name}")
                    if (cat.id!! == food.categoryId) {
                        binding.foodDetailLblCategory.text = cat.name
                        Log.d("TAG", "loadDetails IF: ${cat.name}")
                    }

                }
            }
        })

    }

    private fun objectListener() {
        //  Geri Butonu
        binding.incToolbarFoodDetail.toolbarFoodDetailToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //  Düzenle Butonu
        binding.incToolbarFoodDetail.toolbarFoodDetailBtnEdit.setOnClickListener {
            val dialogBinding: DialogFoodEditBinding = DialogFoodEditBinding.inflate(LayoutInflater.from(requireContext()))
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            val width = (requireContext().resources.displayMetrics.widthPixels * 0.95).toInt()
            dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            dialog.show()

            var position: Int = 0
            //  Find Category Position
            viewModel.categories.value?.let {
                it.forEachIndexed { index, category ->
                    if (category.id == food.categoryId!!) {
                        position = index
                    }
                }
            }

            // Category Adapter
            val categoryAdapterSpinner = CategoryAdapterSpinner(viewModel.categories.value!!)

            dialogBinding.foodEditTxtName.setText(food.name.toString())
            dialogBinding.foodEditTxtIndex.setText(food.index.toString())
            dialogBinding.foodEditTxtCarbonHydrateIn100Gram.setText(food.carbonHydrateIn100Gram.toString())
            dialogBinding.foodEditTxtCalorieIn100Gram.setText(food.calorieIn100Gram.toString())
            dialogBinding.foodEditSpinCategory.adapter = categoryAdapterSpinner
            //dialogBinding.foodEditSpinCategory.setSelection(food.categoryId!! - 1)
            dialogBinding.foodEditSpinCategory.setSelection(position)
            var selectedCategory: Int = food.categoryId!!
            dialogBinding.foodEditSpinCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCategory = (parent!!.getItemAtPosition(position) as Category).id!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

            dialogBinding.foodEditBtnUpdate.setOnClickListener {
                if (dialogBinding.foodEditTxtName.text.toString().isEmpty()) {
                    Snackbar.make(dialogBinding.root, "Lütfen besin adını giriniz.", Snackbar.LENGTH_LONG).show()
                } else if (dialogBinding.foodEditTxtIndex.text.toString().isEmpty()) {
                    Snackbar.make(dialogBinding.root, "Lütfen besin indeksini giriniz.", Snackbar.LENGTH_LONG).show()
                } else if (dialogBinding.foodEditTxtCarbonHydrateIn100Gram.text.toString().isEmpty()) {
                    Snackbar.make(dialogBinding.root, "Lütfen besin karbonhidrat değerini giriniz.", Snackbar.LENGTH_LONG).show()
                } else if (dialogBinding.foodEditTxtCalorieIn100Gram.text.toString().isEmpty()) {
                    Snackbar.make(dialogBinding.root, "Lütfen besin kalori değerini giriniz.", Snackbar.LENGTH_LONG).show()
                } else {
                    val id: Int = food.id!!
                    val name: String = dialogBinding.foodEditTxtName.text.toString()
                    val index: Int = dialogBinding.foodEditTxtIndex.text.toString().toInt()
                    val carbonHydrateIn100Gram: Float = dialogBinding.foodEditTxtCarbonHydrateIn100Gram.text.toString().replace(",", "").toFloat()
                    val calorieIn100Gram: Int = dialogBinding.foodEditTxtCalorieIn100Gram.text.toString().toInt()
                    val categoryId: Int = selectedCategory

                    val updatedFood: Food = Food(id, name, index, carbonHydrateIn100Gram, calorieIn100Gram, categoryId)

                    val result: Int = viewModel.updateFood(updatedFood)
                    if (result > 0) {
                        viewModel.refreshFoodList()
                        Snackbar.make(binding.root, "Güncelleme başarılı!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.blue, requireContext().theme))
                            .show()
                    } else {
                        Snackbar.make(binding.root, "Güncelleme başarısız!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                            .show()
                    }
                    dialog.dismiss()
                }
            }

        }

        //  Sil Butonu
        binding.incToolbarFoodDetail.toolbarFoodDetailBtnDelete.setOnClickListener {
            val dialogBinding: DialogInformationBinding = DialogInformationBinding.inflate(LayoutInflater.from(requireContext()))
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            val width = (requireContext().resources.displayMetrics.widthPixels * 0.95).toInt()
            dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            dialog.show()

            var deleteFood: Food = Food()

            //  Açıklama
            dialogBinding.dialogInformationLblDescription.text = "${food.name} isimli besini silmek istediğinize emin misiniz?"

            //  Onay Butonu
            dialogBinding.dialogInformationBtnOk.setOnClickListener {
                viewModel.foodList.value?.forEachIndexed { index, sfood ->
                    if (sfood.id == food.id) {
                        deleteFood = sfood
                    }
                }
                val result: Int = viewModel.deleteFood(deleteFood.id!!)
                if (result > 0) {
                    Snackbar.make(binding.root, "Besin başarıyla silindi.", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.blue, requireContext().theme))
                        .show()
                    findNavController().popBackStack()
                    viewModel.refreshFoodList()
                } else {
                    Snackbar.make(binding.root, "Silme işlemi başarısız.", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                        .show()
                }
                dialog.dismiss()
            }

            //  İptal Butonu
            dialogBinding.dialogInformationBtnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}