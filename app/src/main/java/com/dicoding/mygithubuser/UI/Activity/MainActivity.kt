package com.dicoding.mygithubuser.UI.Activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.Adapter.UserListAdapter
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.Response.GithubUserListItem
import com.dicoding.mygithubuser.ViewModel.MainViewModel
import com.dicoding.mygithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager

        mainViewModel.users.observe(this){listUser ->
            setUserList(listUser)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.getSearchUser("fitra")
    }

    private fun setUserList(listUserList: List<GithubUserListItem>){
       val listUser = ArrayList<GithubUserListItem>()
       for (users in listUserList){
           listUser.clear()
           listUser.addAll(listUserList)
       }
       val adapter = UserListAdapter(listUser)
       binding.rvGithubUser.adapter = adapter

       adapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
           override fun onItemClicked(data: GithubUserListItem) {
               Intent(this@MainActivity, DetailUserActivity::class.java).also {detail ->
                   detail.putExtra(DetailUserActivity.EXTRA_DETAIL, data.login)
                   startActivity(detail)
               }
           }
       })
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getSearchUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}
