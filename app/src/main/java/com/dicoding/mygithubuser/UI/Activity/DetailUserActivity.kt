package com.dicoding.mygithubuser.UI.Activity

import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Adapter.SectionPagerAdapter
import com.dicoding.mygithubuser.Adapter.UserListAdapter
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.ViewModel.DetailViewModel
import com.dicoding.mygithubuser.ViewModel.MainViewModel
import com.dicoding.mygithubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailMainViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_DETAIL = "extra_detail"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        supportActionBar?.title = getString(R.string.app_detail)

        detailMainViewModel.detailUser.observe(this) { detailUser ->
            if (detailUser != null) {
                setUserDetail(detailUser)
            }
        }

        val detailUser = intent.getStringExtra(EXTRA_DETAIL).toString()
        detailMainViewModel.getDetailUser(detailUser)


        detailMainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    private fun setUserDetail(detailUser: DetailUserResponse){
        detailUserBinding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .into(civDetailProfile)
            tvDetailName.text = detailUser.name
            tvDetailUsername.text = detailUser.login
            tvFollowers.text = StringBuilder(detailUser.followers.toString()).append(" Followers")
            tvFollowing.text = StringBuilder(detailUser.following.toString()).append(" Following")

            val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity)
            sectionPagerAdapter.username = detailUser.login!!

//            val viewPager: ViewPager2 = detailUserBinding.viewPager
            viewPager.adapter = sectionPagerAdapter

            val tabs: TabLayout = detailUserBinding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            detailUserBinding.progressBar.visibility = View.VISIBLE
        }else{
            detailUserBinding.progressBar.visibility = View.GONE
        }
    }
}