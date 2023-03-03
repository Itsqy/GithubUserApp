package com.rifqi.githubuserapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val img = intent.getStringExtra("img")

        if (name != null) {
            detailViewModel.setDetail(name)
        }
        detailViewModel.getDetail().observe(this) {
            binding.apply {
                tvNameUser.text = name.toString()
                Glide.with(this@DetailActivity).load(img).into(ivDetailUser)
                tvFollowers.text = "Followers\n" + it.followers.toString()
                tvFollowing.text = "Following\n" + it.following.toString()
                tvUsernameUser.text = "@" + it.username
            }
        }

        detailViewModel.errorMsg.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        setUpViewPager(name)

    }

    private fun setUpViewPager(name: String?) {
        val parsData = Bundle()
        parsData.putString("name", name)
        val fragadapter = FragmentAdapter(this, parsData)

        binding.vpFragment.adapter = fragadapter
        TabLayoutMediator(binding.tablayout, binding.vpFragment) { i, posision ->
            i.text = resources.getString(title_tabs[posision])
        }.attach()

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private val title_tabs = arrayOf(R.string.following, R.string.follower)
    }
}