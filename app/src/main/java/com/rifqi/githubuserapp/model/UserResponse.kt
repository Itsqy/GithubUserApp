package com.rifqi.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.Field


data class UserResponse(
    @field:SerializedName("items")
    val allUsers: ArrayList<ListUserResponse>
)

@Parcelize
data class ListUserResponse(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,
) : Parcelable
