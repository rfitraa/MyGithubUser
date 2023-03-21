package com.dicoding.mygithubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.databinding.ItemUserListBinding

class FollowerAdapter(private val followerList: ArrayList<DetailUserResponse>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user : DetailUserResponse){
            binding.tvUsername.text = user.login
            binding.tvNoId.text = user.id.toString()
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(binding.civProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FollowerAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        holder.binding(followerList[position])
    }

    override fun getItemCount() = followerList.size
}