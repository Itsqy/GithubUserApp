package com.rifqi.githubuserapp.favorite.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.rifqi.githubuserapp.favorite.database.FavDatabase
import com.rifqi.githubuserapp.favorite.database.FavoriteDao
import com.rifqi.githubuserapp.model.Favorite

class FavoriteRepository(application: Application) {
    private var mFavoriteDao: FavoriteDao

    init {
        val database = FavDatabase.getDB(application)
        mFavoriteDao = database.favDao()
    }

    fun getAllFav(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()

    fun countByName(name: String) = mFavoriteDao.countByname(name)

    fun addFav(favorite: Favorite) = mFavoriteDao.addFav(favorite)


    fun deleteFav(name: String) = mFavoriteDao.deleteFav(name)


}