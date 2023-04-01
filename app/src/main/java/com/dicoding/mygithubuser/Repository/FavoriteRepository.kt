package com.dicoding.mygithubuser.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.mygithubuser.Database.Favorite
import com.dicoding.mygithubuser.Database.FavoriteDao
import com.dicoding.mygithubuser.Database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun insert(favorite: Favorite){
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite){
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}