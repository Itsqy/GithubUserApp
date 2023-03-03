package com.rifqi.githubuserapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.model.UserResponse
import com.rifqi.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val dataDetail = MutableLiveData<ListUserResponse>()

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg



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

}