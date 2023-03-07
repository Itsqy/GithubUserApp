package com.rifqi.githubuserapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqi.githubuserapp.favorite.repo.FavoriteRepository
import com.rifqi.githubuserapp.model.Favorite
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    val dataDetail = MutableLiveData<ListUserResponse>()

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun setDetail(user: String) {
        ApiConfig.getApiService().getDetail(user).enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: Response<ListUserResponse>
            ) {
                if (response.isSuccessful) {
                    dataDetail.postValue(response.body())
                } else {
                    Log.e("error", "$response")
                    _errorMsg.value = response.body().toString()

                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _errorMsg.value = t.message
            }
        })
    }

    fun getDetail(): LiveData<ListUserResponse> {
        return dataDetail
    }

//    check , add and delete favorite

    fun count(name: String) = mFavoriteRepository.countByName(name)

    fun addFav(name: String, imgUrl: String?) =
        CoroutineScope(Dispatchers.IO).launch {
            val fav = Favorite(name, imgUrl)
            mFavoriteRepository.addFav(fav)
        }

    fun delete(name: String) =
        CoroutineScope(Dispatchers.IO).launch { mFavoriteRepository.deleteFav(name) }

}