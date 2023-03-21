package com.dicoding.mygithubuser.Api

import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.Response.GithubResponse
import com.dicoding.mygithubuser.Response.GithubUserListItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<DetailUserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<DetailUserResponse>>
}