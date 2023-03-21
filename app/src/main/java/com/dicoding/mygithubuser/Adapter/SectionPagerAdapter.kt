package com.dicoding.mygithubuser.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.mygithubuser.UI.Fragment.FollowerFragment
import com.dicoding.mygithubuser.UI.Fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = Bundle().apply {
            putString(FollowingFragment.EXTRA_USERNAME, username)
            putString(FollowerFragment.EXTRA_USERNAME, username)
        }
        return fragment as Fragment
    }
}