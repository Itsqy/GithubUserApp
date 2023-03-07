package com.rifqi.githubuserapp.mainmenu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.detail.DetailActivity
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.utils.UserDiffCallBack

class UsersAdapter(val context: Context) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    private val dataUser = ArrayList<ListUserResponse>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgUser: ImageView = view.findViewById(R.id.iv_users)
        val userName: TextView = view.findViewById(R.id.tv_users)

    }


    //    ambil data dari viewmodel yang ngegali di backgorund thread
    fun setListUser(dataUser: ArrayList<ListUserResponse>) {
        val diffutil = UserDiffCallBack(this.dataUser, dataUser)
        val diffResult = DiffUtil.calculateDiff(diffutil)
        this.dataUser.clear()
        this.dataUser.addAll(dataUser)
        diffResult.dispatchUpdatesTo(this)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_row_users, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.userName.text = dataUser[position].username
        Glide.with(context).load(dataUser[position].avatarUrl).into(holder.imgUser)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", dataUser[position].username)
            intent.putExtra("img", dataUser[position].avatarUrl)
            context.startActivity(intent)

        }

    }
}