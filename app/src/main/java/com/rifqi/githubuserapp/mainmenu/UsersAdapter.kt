package com.rifqi.githubuserapp.mainmenu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.githubuserapp.databinding.ItemRowUsersBinding
import com.rifqi.githubuserapp.detail.DetailActivity
import com.rifqi.githubuserapp.model.ListUserResponse
import com.rifqi.githubuserapp.utils.UserDiffCallBack

class UsersAdapter(var context: Context) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    private val dataUser = ArrayList<ListUserResponse>()

    class MyViewHolder(private val binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: ListUserResponse) {
            with(binding) {
                tvUsers.text = note.username
                Glide.with(itemView.context).load(note.avatarUrl).into(ivUsers)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("name", note.username)
                    intent.putExtra("img", note.avatarUrl)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }


    //    ambil data dari viewmodel yang ngegali di backgorund thread
    fun setListUser(user: ArrayList<ListUserResponse>) {

        val diffCallback = UserDiffCallBack(this.dataUser, user)
        val difresult = DiffUtil.calculateDiff(diffCallback)
        this.dataUser.clear()
        this.dataUser.addAll(user)
        notifyDataSetChanged()
        difresult.dispatchUpdatesTo(this)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataUser[position])

    }
}