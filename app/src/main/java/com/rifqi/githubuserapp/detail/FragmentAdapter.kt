package com.rifqi.githubuserapp.detail

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rifqi.githubuserapp.detail.followers.FollowersFragment
import com.rifqi.githubuserapp.detail.following.FollowingFragment

class FragmentAdapter(context: AppCompatActivity, data: Bundle) : FragmentStateAdapter(context) {

    val data: Bundle = data
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }

        fragment!!.arguments = this.data
        return fragment


    }
}