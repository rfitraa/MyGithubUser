package com.dicoding.mygithubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.Api.ApiConfig
import com.dicoding.mygithubuser.Response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _followerList = MutableLiveData<ArrayList<DetailUserResponse>>()
    val followerList: LiveData<ArrayList<DetailUserResponse>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "Followers Fragment"
    }

    fun getFollowers(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<DetailUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DetailUserResponse>>,
                response: Response<ArrayList<DetailUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followerList.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<DetailUserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure : ${t.message}")
            }
        })
    }
}