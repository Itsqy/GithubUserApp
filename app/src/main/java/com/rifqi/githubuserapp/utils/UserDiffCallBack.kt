package com.rifqi.githubuserapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.rifqi.githubuserapp.model.ListUserResponse

class UserDiffCallBack(
    private val mOldUserList: List<ListUserResponse>,
    private val mNewUserList: List<ListUserResponse>,
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldListUser = mOldUserList[oldItemPosition]
        val newListUser = mNewUserList[newItemPosition]
        return oldListUser.name == newListUser.name && oldListUser.avatarUrl == newListUser.avatarUrl
    }
}