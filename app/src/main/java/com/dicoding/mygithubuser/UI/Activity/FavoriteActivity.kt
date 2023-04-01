package com.dicoding.mygithubuser.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Adapter.FavoriteAdapter
import com.dicoding.mygithubuser.Adapter.SectionPagerAdapter
import com.dicoding.mygithubuser.Database.Favorite
import com.dicoding.mygithubuser.ViewModel.FavoriteViewModel
import com.dicoding.mygithubuser.ViewModel.FavoriteViewModelFactory
import com.dicoding.mygithubuser.databinding.ActivityFavoriteBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding?= null
    private val binding get() = _binding

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Favorite List"

        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(true)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this) { favoriteList ->
            val adapter = FavoriteAdapter(favoriteList as ArrayList<Favorite>)
            binding?.rvFavorites?.adapter = adapter
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}