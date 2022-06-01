package com.example.kmmandroidnative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.kmmandroidnative.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val breeds = listOf(
        "corgi",
        "husky",
        "shiba"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val adapter = ViewPagerAdapter(breeds, supportFragmentManager, lifecycle)
        binding.viewPagerDog.adapter = adapter

        TabLayoutMediator(binding.tabDogBreed, binding.viewPagerDog) { tab, position ->
            tab.text = breeds[position]
        }.attach()
    }
}