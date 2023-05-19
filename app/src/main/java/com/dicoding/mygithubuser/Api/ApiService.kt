package com.dicoding.mygithubuser.Api


import com.dicoding.mygithubuser.BuildConfig
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.Response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<DetailUserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<DetailUserResponse>>
}