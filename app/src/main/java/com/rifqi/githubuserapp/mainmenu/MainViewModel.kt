package com.rifqi.githubuserapp.mainmenu

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.model.UserResponse
import com.rifqi.githubuserapp.network.ApiConfig
import com.rifqi.githubuserapp.settingpref.ThemePreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val application: Application) : ViewModel() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val pref: ThemePreferences = ThemePreferences.getInstance(application.dataStore)

    //    private val _users = MutableLiveData<List<ListUserResponse>>()
    val users = MutableLiveData<ArrayList<ListUserResponse>>()


    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    fun showListUser(testQuery: String) {
        _loading.value = true
        ApiConfig.getApiService().getUsers("$testQuery").enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _loading.value = true
                if (response.isSuccessful) {
                    _loading.value = false
                    val responseBody = response.body()
                    users.postValue(responseBody!!.allUsers)
                } else {
                    _loading.value = false
                    _errorMsg.value = response.body().toString()
                }

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _errorMsg.value = t.message

            }
        })
    }

    fun getUsers(): LiveData<ArrayList<ListUserResponse>> {
        return users
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getTheme().asLiveData()
    }

    fun saveThemeSetting(isDarkActive: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkActive)
        }
    }

}