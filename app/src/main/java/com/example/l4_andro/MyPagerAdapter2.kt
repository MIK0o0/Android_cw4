package com.example.l4_andro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter2(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return the proper fragment for each position value
        return when(position) {
            0 -> Fragment1()
            1 -> Fragment2()
            else -> Fragment1()
        }
    }

}
