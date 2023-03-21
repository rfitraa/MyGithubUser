package com.dicoding.mygithubuser.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.Adapter.FollowerAdapter
import com.dicoding.mygithubuser.Response.DetailUserResponse
import com.dicoding.mygithubuser.ViewModel.FollowerViewModel
import com.dicoding.mygithubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding?= null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowerViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        followerViewModel.followerList.observe(viewLifecycleOwner, {followerList ->
            setDataFollowers(followerList)
        })

        followerViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        val username = arguments?.getString(EXTRA_USERNAME).toString()
        followerViewModel.getFollowers(username)
    }

    private fun setDataFollowers(followerList: ArrayList<DetailUserResponse>) {
        val listUserFollower = ArrayList<DetailUserResponse>()
        for (followers in followerList){
            listUserFollower.clear()
            listUserFollower.addAll(followerList)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollower.layoutManager = layoutManager

        val adapter = FollowerAdapter(followerList)
        binding.rvFollower.adapter = adapter
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