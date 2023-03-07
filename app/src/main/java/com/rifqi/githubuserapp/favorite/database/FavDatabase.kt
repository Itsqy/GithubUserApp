package com.rifqi.githubuserapp.favorite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rifqi.githubuserapp.model.Favorite


@Database(entities = [Favorite::class], version = 1)
abstract class FavDatabase : RoomDatabase() {
    abstract fun favDao(): FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: FavDatabase? = null

        @JvmStatic
        fun getDB(context: Context): FavDatabase {
            if (INSTANCE == null) {
                synchronized(FavDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavDatabase::class.java,
                        "myDB"
                    ).build()
                }
            }
            return INSTANCE as FavDatabase
        }

    }


}