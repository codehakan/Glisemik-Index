package com.hakanbey.glisemikindeks.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hakanbey.glisemikindeks.FragmentFactory
import com.hakanbey.glisemikindeks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivityMainBinding

    //  Fragment Factory
    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}