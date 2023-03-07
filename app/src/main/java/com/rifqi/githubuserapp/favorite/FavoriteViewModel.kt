package com.rifqi.githubuserapp.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqi.githubuserapp.favorite.repo.FavoriteRepository
import com.rifqi.githubuserapp.model.Favorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository?

    init {
        mFavoriteRepository = FavoriteRepository(application)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>? = mFavoriteRepository?.getAllFav()


}