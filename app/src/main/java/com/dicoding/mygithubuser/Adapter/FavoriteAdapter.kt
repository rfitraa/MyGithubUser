package com.dicoding.mygithubuser.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.Database.Favorite
import com.dicoding.mygithubuser.Helper.FavoriteDiffCallback
import com.dicoding.mygithubuser.UI.Activity.DetailUserActivity
import com.dicoding.mygithubuser.databinding.ItemUserListBinding

class FavoriteAdapter(private val listFavorite: ArrayList<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    fun setListFavorite(favorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, favorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(favorite)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteViewHolder(private val binding: ItemUserListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvNoId.text = favorite.id.toString()
                tvUsername.text = favorite.username
                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_DETAIL, favorite.username)
                    it.context.startActivity(intent)
                }
            }
            Glide.with(itemView)
                .load(favorite.avatarUrl)
                .into(binding.civProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = listFavorite[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
}