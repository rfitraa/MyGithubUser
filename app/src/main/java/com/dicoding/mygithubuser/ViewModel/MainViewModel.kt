package com.dicoding.mygithubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.Api.ApiConfig
import com.dicoding.mygithubuser.Response.GithubResponse
import com.dicoding.mygithubuser.Response.GithubUserListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _users = MutableLiveData<List<GithubUserListItem>>()
    val users: LiveData<List<GithubUserListItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSearchUser(input: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(input)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _users.value = response.body()?.items
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}