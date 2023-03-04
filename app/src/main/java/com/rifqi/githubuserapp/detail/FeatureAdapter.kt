package com.rifqi.githubuserapp.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.detail.DetailActivity
import com.rifqi.githubuserapp.model.ListUserResponse

class FeatureAdapter(val context: Context) :
    RecyclerView.Adapter<FeatureAdapter.MyViewHolder>() {
    private val dataUser = ArrayList<ListUserResponse>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgUser: ImageView = view.findViewById(R.id.iv_users)
        val userName: TextView = view.findViewById(R.id.tv_users)

    }

    fun setListUser(users: ArrayList<ListUserResponse>) {
        dataUser.clear()
        dataUser.addAll(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_row_users, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataUser!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.userName.text = dataUser!![position].username
        Glide.with(context).load(dataUser!![position].avatarUrl).into(holder.imgUser)
    }
}