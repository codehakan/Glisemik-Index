package com.hakanbey.glisemikindeks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hakanbey.glisemikindeks.BuildConfig
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterSpinner
import com.hakanbey.glisemikindeks.adapter.FoodAdapter
import com.hakanbey.glisemikindeks.databinding.FragmentMainBinding
import com.hakanbey.glisemikindeks.extension.*
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.service.Database
import com.hakanbey.glisemikindeks.viewmodel.GlisemikViewModel
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec.WRAP
import com.skydoves.balloon.overlay.BalloonOverlayAnimation
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
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
class MainFragment
@Inject constructor(
    private val viewModel: GlisemikViewModel,
    private val database: Database
) : Fragment(R.layout.fragment_main) {
    //  Binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //  Food Adapter
    private lateinit var foodAdapter: FoodAdapter

    //  Category Adapter
    private lateinit var categoryAdapter: CategoryAdapterSpinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (_binding == null) {
            _binding = FragmentMainBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Initialize RecyclerView
        initRecyclerView()

        //  Object Listener
        objectListener()

        //  ToolTips
        tooltipVersionControl()
    }

    private fun initRecyclerView() {
        viewModel.foodList.observe(this, {
            it?.let {
                foodAdapter = FoodAdapter(it)
                binding.mainRecyclerViewFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.mainRecyclerViewFood.adapter = foodAdapter
            }
        })

        viewModel.categories.observe(this, {
            it?.let {
                categoryAdapter = CategoryAdapterSpinner(it)
                binding.incToolbarMain.toolbarMainSpinner.adapter = categoryAdapter
            }
        })
    }

    private fun objectListener() {
        //  Spinner
        binding.incToolbarMain.toolbarMainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoryId: Int = (parent?.getItemAtPosition(position) as Category).id!!
                foodAdapter.foodFilter(categoryId)
                binding.mainColumnLblName.text = "Besin (${foodAdapter.itemCount})"
                if (foodAdapter.itemCount > 0) {
                    binding.mainRecyclerViewLblEmpty.visibility = View.GONE
                } else {
                    binding.mainRecyclerViewLblEmpty.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //  Arama Yapıldığında
        binding.incToolbarMain.toolbarMainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        foodAdapter.searchFood(query)
                    } else {
                        foodAdapter.searchFood("")
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isNotEmpty()) {
                        foodAdapter.searchFood(newText)
                    } else {
                        foodAdapter.searchFood("")
                    }
                }
                return true
            }

        })

        //  Arama Kutucuğu Açıldığında
        binding.incToolbarMain.toolbarMainSearchView.setOnSearchClickListener {
            binding.incToolbarMain.toolbarMainSpinner.hide()
        }

        //  Arama Kutucuğu Kapatıldığında
        binding.incToolbarMain.toolbarMainSearchView.setOnCloseListener {
            binding.incToolbarMain.toolbarMainSpinner.show()
            foodAdapter.searchFood("")
            false
        }

        //  Yeni Food Ekle
        binding.mainFabMenuFoodAdd.setOnClickListener {
            navigateSafe(R.id.action_mainFragment_to_foodAddFragment)
        }

        //  Yeni Kategori Ekle
        binding.mainFabMenuCategories.setOnClickListener {
            navigateSafe(R.id.action_mainFragment_to_categoriesFragment)
        }
    }

    private fun newBalloon(text: String): Balloon {
        return Balloon.Builder(requireContext())
            .setWidthRatio(0.5f)
            .setHeight(WRAP)
            .setText(text)
            .setTextColorResource(R.color.white)
            .setTextSize(14f)
            .setTextTypeface(R.font.bold) //.setIconDrawableResource(R.drawable.ic_app_icon_no_background)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.blue)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setIsVisibleOverlay(true)
            .setOverlayShape(BalloonOverlayRoundRect(12f, 12f))
            .setOverlayColorResource(R.color.overlay)
            .setOverlayPadding(3f)
            .setOverlayPaddingColorResource(R.color.white)
            .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE)
            .build()
    }

    private fun tooltipVersionControl() {
        val savedVersion: Int = getToolTipVersion(requireContext(), requireActivity().getString(R.string.home_tooltip_version))
        val appVersion = BuildConfig.VERSION_CODE
        if (savedVersion < appVersion) {
            saveToolTipVersion(requireContext(), requireActivity().getString(R.string.home_tooltip_version), appVersion)
            showToolTips()
        }
    }

    private fun showToolTips() {
        val spinnerBalloon = newBalloon("Kategoriyi buradan değiştirebilirsiniz.")
        val searchBalloon = newBalloon("Besinler içerisinde aramayı buradan yapabilirsiniz.")
        val recyclerView = newBalloon("Besinler burada listelenir. Detayı görmek için herhangi bir tanesine tıklayın.")
        val fab = newBalloon("İşlem menüsünü buradan açabilirsiniz.")

        spinnerBalloon
            .relayShowAlignBottom(searchBalloon, binding.incToolbarMain.toolbarMainSearchView)
            .relayShowAlignTop(recyclerView, binding.mainRecyclerViewFood)
            .relayShowAlignTop(fab, binding.mainFabAdd)
        spinnerBalloon.showAlignBottom(binding.incToolbarMain.toolbarMainSpinner)

    }
}