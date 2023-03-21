package com.dicoding.mygithubuser.Response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
	@field:SerializedName("items")
	val items: List<GithubUserListItem>
)

data class GithubUserListItem(
	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)
