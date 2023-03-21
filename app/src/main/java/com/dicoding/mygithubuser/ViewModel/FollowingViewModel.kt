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

class FollowingViewModel: ViewModel() {
    private val _followinglist = MutableLiveData<ArrayList<DetailUserResponse>>()
    val followingList: LiveData<ArrayList<DetailUserResponse>> = _followinglist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "Following Fragment"
    }

    fun getFollowing(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<DetailUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DetailUserResponse>>,
                response: Response<ArrayList<DetailUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followinglist.value = response.body()
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