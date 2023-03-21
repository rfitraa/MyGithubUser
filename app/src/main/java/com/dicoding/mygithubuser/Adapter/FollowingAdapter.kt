package com.dicoding.mygithubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.databinding.ItemUserListBinding

class FollowingAdapter(private val followingList: ArrayList<DetailUserResponse>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user : DetailUserResponse){
            binding.tvUsername.text = user.login
            binding.tvNoId.text = user.id.toString()
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(binding.civProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.ViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FollowingAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ViewHolder, position: Int) {
        holder.binding(followingList[position])
    }

    override fun getItemCount()= followingList.size
}