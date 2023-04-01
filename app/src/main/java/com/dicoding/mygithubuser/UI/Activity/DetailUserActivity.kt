package com.dicoding.mygithubuser.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Adapter.SectionPagerAdapter
import com.dicoding.mygithubuser.Database.Favorite
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.ViewModel.DetailViewModel
import com.dicoding.mygithubuser.ViewModel.FavoriteViewModel
import com.dicoding.mygithubuser.ViewModel.FavoriteViewModelFactory
import com.dicoding.mygithubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var button: Boolean = false
    private var favorite: Favorite? = null

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

        favoriteViewModel = obtainViewModel(this@DetailUserActivity)

        detailViewModel.detailUser.observe(this) { detailUser ->
            if (detailUser != null) {
                setUserDetail(detailUser)
                favorite = Favorite(detailUser.id!!, detailUser.login)
                favoriteViewModel.getAllFavorites().observe(this){favoriteUser ->
                    if (favoriteUser != null){
                        setUserFavorite(favoriteUser, detailUser)
                    }
                }
                setFavoriteEvent(detailUser)
            }
        }

        var detailUser = intent.getStringExtra(EXTRA_DETAIL).toString()
        detailViewModel.getDetailUser(detailUser)


        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setFavoriteEvent(detailUser: DetailUserResponse) {
        detailUserBinding.fabFavorite.setOnClickListener {
            if (!button){
                button = true
                detailUserBinding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                insertToFavorite(detailUser)
            }else{
                button = false
                detailUserBinding.fabFavorite.setImageResource(R.drawable.ic_unfavorite)
                deleteFromFavorite(detailUser)
            }
        }
    }

    private fun deleteFromFavorite(detailUser: DetailUserResponse) {
        favorite.let { favorite ->
            favorite?.id = detailUser.id!!
            favorite?.username = detailUser.login
            favorite?.avatarUrl = detailUser.avatarUrl
            favoriteViewModel.delete(favorite as Favorite)
            Toast.makeText(this, "Successfully Remove from Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertToFavorite(detailUser: DetailUserResponse) {
        favorite.let { favorite ->
            favorite?.id = detailUser.id!!
            favorite?.username = detailUser.login
            favorite?.avatarUrl = detailUser.avatarUrl
            favoriteViewModel.insert(favorite as Favorite)
            Toast.makeText(this, "Successfully Added to Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserFavorite(favoriteUser: List<Favorite>, detailUser: DetailUserResponse) {
        for (fav in favoriteUser){
            if (detailUser.id == fav.id){
                button = true
                detailUserBinding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
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