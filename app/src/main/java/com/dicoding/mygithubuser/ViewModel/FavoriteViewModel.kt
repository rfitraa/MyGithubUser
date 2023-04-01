package com.dicoding.mygithubuser.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.Database.Favorite
import com.dicoding.mygithubuser.Repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}