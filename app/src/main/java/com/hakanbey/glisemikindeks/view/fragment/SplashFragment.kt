package com.hakanbey.glisemikindeks.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hakanbey.glisemikindeks.R
import com.hakanbey.glisemikindeks.databinding.FragmentSplashBinding
import com.hakanbey.glisemikindeks.extension.navigateSafe
import com.hakanbey.glisemikindeks.service.Database
import com.hakanbey.glisemikindeks.util.Util
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
class SplashFragment
@Inject constructor(
    private val viewModel: GlisemikViewModel,
    private val database: Database
) : Fragment(R.layout.fragment_splash) {
    //  Binding
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.foodList.postValue(database.getAllFoods())
        viewModel.categories.postValue(database.getCategories())

        viewModel.foodList.observe(this, {
            if (it.isNotEmpty()) {
                Log.d("TAG", "viewModel.foodList: DOLU")
                navigateSafe(R.id.action_splashFragment_to_mainFragment)
            } else {
                Log.d("TAG", "viewModel.foodList: BOŞ")
            }
        })

        Util.dataStatus.observe(this) {
            if (it) {
                //Util.categories.postValue(database.getCategories())
                //Util.foodList.postValue(database.getAllFoods())
                viewModel.foodList.postValue(database.getAllFoods())
                viewModel.categories.postValue(database.getCategories())
            }
        }
    }
}