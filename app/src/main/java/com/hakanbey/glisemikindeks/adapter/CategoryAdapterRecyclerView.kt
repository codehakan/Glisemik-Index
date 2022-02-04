package com.hakanbey.glisemikindeks.adapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.databinding.DialogCategoryAddBinding
import com.hakanbey.glisemikindeks.databinding.DialogInformationBinding
import com.hakanbey.glisemikindeks.databinding.SingleCategoryBinding
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.service.Database
import com.hakanbey.glisemikindeks.viewmodel.GlisemikViewModel
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 4.02.2022               │
//└──────────────────────────┘

class CategoryAdapterRecyclerView
@Inject constructor(
    private val viewModel: GlisemikViewModel,
    private val database: Database
) : RecyclerView.Adapter<CategoryAdapterRecyclerView.MyViewHolder>() {
    val categories: ArrayList<Category> = ArrayList(viewModel.categories.value!!)

    class MyViewHolder(val binding: SingleCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SingleCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category: Category = categories[position]

        holder.binding.apply {
            //  Category Name
            singleCategoryLblTitle.text = category.name

            //  Category Edit Button
            singleCategoryBtnEdit.setOnClickListener {
                val dialogBinding: DialogCategoryAddBinding = DialogCategoryAddBinding.inflate(LayoutInflater.from(root.context))
                val dialog = Dialog(root.context)
                dialog.setContentView(dialogBinding.root)
                val width = (root.context.resources.displayMetrics.widthPixels * 0.95).toInt()
                dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(true)
                dialog.show()

                //  Güncel Kategori İsmi
                dialogBinding.dialogCategoryAddTxtName.setText(category.name.toString())

                //  Buton İsmini Değiştir
                dialogBinding.dialogCategoryAddBtn.text = "Güncelle"
                dialogBinding.dialogCategoryAddBtn.setOnClickListener {
                    if (dialogBinding.dialogCategoryAddTxtName.text.toString().isEmpty()) {
                        Snackbar.make(dialogBinding.root, "Kategori ismini boş bırakmayın..", Snackbar.LENGTH_LONG).show()
                    } else {
                        category.name = dialogBinding.dialogCategoryAddTxtName.text.toString()
                        val result: Int = database.updateCategory(category)
                        if (result > 0) {
                            Snackbar.make(dialogBinding.root, "Kategori başarıyla güncellendi.", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(root.context.resources.getColor(R.color.blue, root.context.theme))
                                .show()
                            viewModel.refreshCategories()
                        } else {
                            Snackbar.make(dialogBinding.root, "Kategori güncelleme başarısız.", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(root.context.resources.getColor(R.color.index_high, root.context.theme))
                                .show()
                        }
                        dialog.dismiss()
                    }
                }

            }

            //  Category Delete Button
            singleCategoryBtnDelete.setOnClickListener {
                val dialogBinding: DialogInformationBinding = DialogInformationBinding.inflate(LayoutInflater.from(root.context))
                val dialog = Dialog(root.context)
                dialog.setContentView(dialogBinding.root)
                val width = (root.context.resources.displayMetrics.widthPixels * 0.95).toInt()
                dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(true)
                dialog.show()

                //  Açıklama
                dialogBinding.dialogInformationLblDescription.text = "${category.name} isimli kategoriyi silmek istediğinize emin misiniz?\n\nKategoriye ait tüm besinler silinecektir."

                //  Onay Butonu
                dialogBinding.dialogInformationBtnOk.setOnClickListener {
                    val result: Int = database.deleteCategory(category.id!!)
                    if (result > 0) {
                        Snackbar.make(root, "Kategori başarıyla silindi.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(root.context.resources.getColor(R.color.blue, root.context.theme))
                            .show()
                        viewModel.refreshFoodList()
                        viewModel.refreshCategories()
                    } else {
                        Snackbar.make(dialogBinding.root, "Kategori silinemedi.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(root.context.resources.getColor(R.color.index_high, root.context.theme))
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

    override fun getItemCount(): Int {
        return categories.count()
    }
}