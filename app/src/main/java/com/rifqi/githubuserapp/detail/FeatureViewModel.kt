package com.rifqi.githubuserapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.network.ApiConfig
import com.rifqi.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeatureViewModel : ViewModel() {

    val dataFollowers = MutableLiveData<ArrayList<ListUserResponse>>()
    val dataFollowing = MutableLiveData<ArrayList<ListUserResponse>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    private val _msgError = MutableLiveData<Event<String>>()
    val msgError: LiveData<Event<String>> = _msgError

    //followers
    fun setFollowers(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
        client.getFollowers(userName).enqueue(object : Callback<ArrayList<ListUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<ListUserResponse>>,
                response: Response<ArrayList<ListUserResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    dataFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ListUserResponse>>, t: Throwable) {
                _isLoading.value = false
                _msgError.value = Event(t.message.toString())
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<ListUserResponse>> {
        return dataFollowers
    }

//    following

    fun setFollowing(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
        client.getFollowing(userName).enqueue(object : Callback<ArrayList<ListUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<ListUserResponse>>,
                response: Response<ArrayList<ListUserResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    dataFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ListUserResponse>>, t: Throwable) {
                _isLoading.value = false
                _msgError.value = Event(t.message.toString())
            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<ListUserResponse>> {
        return dataFollowing

    }

}