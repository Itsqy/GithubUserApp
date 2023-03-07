package com.rifqi.githubuserapp.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import  androidx.lifecycle.ViewModelProvider
import com.rifqi.githubuserapp.detail.DetailViewModel
import com.rifqi.githubuserapp.favorite.FavoriteViewModel
import com.rifqi.githubuserapp.mainmenu.MainViewModel
import com.rifqi.githubuserapp.settingpref.DarkModeActivity

class ViewModelFactory constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mApplication) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application:Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}