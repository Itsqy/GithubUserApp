package com.rifqi.githubuserapp.mainmenu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.databinding.ActivityMainBinding
import com.rifqi.githubuserapp.favorite.FavoriteActivity
import com.rifqi.githubuserapp.settingpref.DarkModeActivity
import com.rifqi.githubuserapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels<MainViewModel>() {
        ViewModelFactory.getInstance(application)
    }
//    private lateinit var adapter: UsersAdapter
    private var isChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = UsersAdapter(this)
        mainViewModel.showListUser("q")

//        get data from viewmodel to adapter throw to setter
        mainViewModel.getUsers().observe(this) { users ->
            adapter.setListUser(users)

        }

        mainViewModel.errorMsg.observe(this) { msg ->
            Toast.makeText(this, "error : $msg", Toast.LENGTH_SHORT).show()
        }
//        bind rv to adapter
        binding?.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = adapter
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty() && query != null) {
                    mainViewModel.showListUser(query)
                    return true
                } else {
                    Toast.makeText(this@MainActivity, "User Tidak Ditemukan", Toast.LENGTH_SHORT)
                        .show()
                    return false

                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() && newText != null) {
                    mainViewModel.showListUser(newText)
                }
                return false
            }
        }

        )

        isshowLoading()
        setTheme()

    }

    private fun setTheme() {
        mainViewModel.getThemeSetting().observe(this) { isDarkActive: Boolean ->

            if (isDarkActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }


    fun isshowLoading() {
        mainViewModel.loading.observe(this) {
            if (it) {
                binding.pbUsers.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.INVISIBLE

            } else {
                binding.pbUsers.visibility = View.INVISIBLE
                binding.rvUsers.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.all_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.sett_preff -> startActivity(Intent(this, DarkModeActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}