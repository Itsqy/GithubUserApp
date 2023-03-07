package com.rifqi.githubuserapp.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.githubuserapp.databinding.ActivityFavoriteBinding
import com.rifqi.githubuserapp.mainmenu.UsersAdapter
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private val favViewModel: FavoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var userAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "My Favorite"
        setUpAdapter()


    }

    private fun setUpAdapter() {
        favViewModel.getAllFavorites()?.observe(this) { favorites ->
            val allFavUser = ArrayList<ListUserResponse>()
            for (user in favorites) {
                val mappingUser = ListUserResponse(
                    username = user.name,
                    avatarUrl = user.imgUrl,
                )
                allFavUser.add(mappingUser)
            }
            userAdapter.setListUser(allFavUser)
        }
        userAdapter = UsersAdapter(this)
        binding?.apply {
            rvFavorites.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorites.setHasFixedSize(true)
            rvFavorites.adapter = userAdapter
        }


    }
}