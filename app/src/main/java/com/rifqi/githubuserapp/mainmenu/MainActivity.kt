package com.rifqi.githubuserapp.mainmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.githubuserapp.databinding.ActivityMainBinding
import com.rifqi.githubuserapp.model.ListUserResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        bind  viewModel
//        mainViewModel = MainViewModel()
//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = adapter
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isEmpty()) {
                    Toast.makeText(this@MainActivity, "User Tidak Ditemukan", Toast.LENGTH_SHORT)
                        .show()
                    return true
                } else {

                    mainViewModel.showListUser(query.toString())

                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {
                    mainViewModel.showListUser(newText.toString())

                    Toast.makeText(
                        this@MainActivity, "User tidak ditemukan", Toast.LENGTH_SHORT
                    ).show()

                } else {
                    mainViewModel.showListUser(newText.toString())

                }
                return false
            }
        }

        )

        isshowLoading()

    }

    fun showLoading(status: Boolean) {
        if (status) {
            binding.pbUsers.visibility = View.VISIBLE
        } else {
            binding.pbUsers.visibility = View.INVISIBLE
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

}