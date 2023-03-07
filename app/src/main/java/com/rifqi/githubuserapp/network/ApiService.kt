package com.rifqi.githubuserapp.network

import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") q: String
    ): retrofit2.Call<UserResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): retrofit2.Call<ListUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): retrofit2.Call<ArrayList<ListUserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): retrofit2.Call<ArrayList<ListUserResponse>>


}