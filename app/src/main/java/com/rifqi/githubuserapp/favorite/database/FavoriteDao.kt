package com.rifqi.githubuserapp.favorite.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rifqi.githubuserapp.model.Favorite


@Dao
interface FavoriteDao {


    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT COUNT(*) FROM favorite WHERE favorite.name = :name ")
    fun countByname(name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFav(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE favorite.name = :name")
    fun deleteFav(name: String): Int


}