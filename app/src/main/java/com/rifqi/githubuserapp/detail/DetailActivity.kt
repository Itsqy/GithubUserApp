package com.rifqi.githubuserapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.databinding.ActivityDetailBinding
import com.rifqi.githubuserapp.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

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

        setupFavButton(name.toString(), img)

        detailViewModel.errorMsg.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        setUpViewPager(name)

    }

    private fun setupFavButton(name: String, imgurl: String?) {
        var favorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.count(name)
            withContext(Dispatchers.Main) {
                if (count == 0) {
                    binding.fabFav.setImageResource(R.drawable.favorite_border)
                    favorite = false
                } else {
                    favorite = true
                    binding.fabFav.setImageResource(R.drawable.favorite_filled)
                }
            }
        }

        binding.fabFav.setOnClickListener {
            favorite = !favorite
            if (favorite) {
                detailViewModel.addFav(name, imgurl)
                Toast.makeText(this, getString(R.string.success_add_fav), Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.delete(name)
                Toast.makeText(this, getString(R.string.success_delete_fav), Toast.LENGTH_SHORT)
                    .show()
            }

            if (favorite) {
                binding.fabFav.setImageResource(R.drawable.favorite_filled)
            } else {
                binding.fabFav.setImageResource(R.drawable.favorite_border)
            }
        }
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