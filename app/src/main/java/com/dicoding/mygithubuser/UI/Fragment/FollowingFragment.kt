package com.dicoding.mygithubuser.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.Adapter.FollowingAdapter
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.ViewModel.FollowingViewModel
import com.dicoding.mygithubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding?= null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)

        followingViewModel.followingList.observe(viewLifecycleOwner, {followingList ->
            setDataFollowing(followingList)
        })

        followingViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        val username = arguments?.getString(EXTRA_USERNAME).toString()
        followingViewModel.getFollowing(username)
    }

    private fun setDataFollowing(followingList: ArrayList<DetailUserResponse>) {
        val listUserFollowing = ArrayList<DetailUserResponse>()
        for (following in followingList){
            listUserFollowing.clear()
            listUserFollowing.addAll(followingList)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = layoutManager

        val adapter = FollowingAdapter(followingList)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}