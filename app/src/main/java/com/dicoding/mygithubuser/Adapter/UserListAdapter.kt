package com.dicoding.mygithubuser.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Response.GithubUserListItem
import com.dicoding.mygithubuser.databinding.ItemUserListBinding

class UserListAdapter(private val listUser: List<GithubUserListItem>): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUserListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user : GithubUserListItem){
            binding.tvUsername.text = user.login
            binding.tvNoId.text = user.id.toString()
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(binding.civProfile)
        }
    }
    override fun getItemCount() = listUser.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(
                listUser[holder.adapterPosition]
            )
        }
    }


}