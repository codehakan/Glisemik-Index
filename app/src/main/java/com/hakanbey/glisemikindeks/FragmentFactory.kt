package com.hakanbey.glisemikindeks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hakanbey.glisemikindeks.adapter.CategoryAdapterRecyclerView
import com.hakanbey.glisemikindeks.service.Database
import com.hakanbey.glisemikindeks.view.fragment.*
import com.hakanbey.glisemikindeks.viewmodel.GlisemikViewModel
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 03.02.2022               │
//└──────────────────────────┘

class FragmentFactory
@Inject constructor(
    private val viewModel: GlisemikViewModel,
    private val database: Database
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SplashFragment::class.java.name -> SplashFragment(viewModel, database)
            MainFragment::class.java.name -> MainFragment(viewModel, database)
            FoodAddFragment::class.java.name -> FoodAddFragment(viewModel)
            FoodDetailFragment::class.java.name -> FoodDetailFragment(viewModel)
            CategoriesFragment::class.java.name -> CategoriesFragment(viewModel, database)
            else -> super.instantiate(classLoader, className)
        }
    }
}