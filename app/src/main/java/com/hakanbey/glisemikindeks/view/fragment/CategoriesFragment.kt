package com.hakanbey.glisemikindeks.view.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterRecyclerView
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterSpinner
import com.hakanbey.glisemikindeks.databinding.DialogCategoryAddBinding
import com.hakanbey.glisemikindeks.databinding.FragmentCategoriesBinding
import com.hakanbey.glisemikindeks.service.Database
import com.hakanbey.glisemikindeks.viewmodel.GlisemikViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 4.02.2022               │
//└──────────────────────────┘

@AndroidEntryPoint
class CategoriesFragment
@Inject constructor(
    private val viewModel: GlisemikViewModel,
    private val database: Database
) : Fragment(R.layout.fragment_categories) {
    //  Binding
    private lateinit var binding: FragmentCategoriesBinding

    //  Categories Adapter
    private lateinit var adapterCategories: CategoryAdapterRecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCategories = CategoryAdapterRecyclerView(viewModel, database)

        //  Initialize RecyclerView
        initRecyclerView()

        //  Object Listener
        objectListener()

    }

    private fun objectListener() {
        //  Geri
        binding.incToolbarCategories.toolbarCategoriesToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //  Kategori Ekle
        binding.incToolbarCategories.toolbarCategoriesBtnAdd.setOnClickListener {
            val dialogBinding: DialogCategoryAddBinding = DialogCategoryAddBinding.inflate(LayoutInflater.from(requireContext()))
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            val width = (requireContext().resources.displayMetrics.widthPixels * 0.95).toInt()
            dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            dialog.show()

            dialogBinding.dialogCategoryAddBtn.setOnClickListener {
                if (dialogBinding.dialogCategoryAddTxtName.text.toString().isEmpty()) {
                    Snackbar.make(dialogBinding.root, "Lütfen kategori adını giriniz.", Snackbar.LENGTH_LONG).show()
                } else {
                    val name: String = dialogBinding.dialogCategoryAddTxtName.text.toString()
                    val result = database.addCategory(name)
                    if (result > 0) {
                        viewModel.categories.postValue(database.getCategories())
                        Snackbar.make(binding.root, "Kategori başarıyla eklendi.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.index_low, requireContext().theme))
                            .show()
                        viewModel.refreshCategories()
                    } else {
                        Snackbar.make(dialogBinding.root, "Kategori ekleme başarısız.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.index_high, requireContext().theme))
                            .show()
                    }
                    dialog.dismiss()
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.categories.observe(this, {
            it?.let {
                adapterCategories = CategoryAdapterRecyclerView(viewModel, database)
                binding.categoriesRecyclerView.adapter = adapterCategories
                binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        })
    }


}