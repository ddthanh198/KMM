package com.example.kmmandroidnative

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kmmandroidnative.dog_list.DogImageListFragment

class ViewPagerAdapter(private val breeds : List<String>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return breeds.size
    }

    override fun createFragment(position: Int): Fragment {
        return DogImageListFragment.newInstance(breeds[position])
    }
}