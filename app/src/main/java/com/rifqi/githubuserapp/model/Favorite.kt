package com.rifqi.githubuserapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    var name: String = "",
    var imgUrl: String? = null,
)
