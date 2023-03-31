package com.dicoding.mygithubuser.UI.Activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.Adapter.UserListAdapter
import com.dicoding.mygithubuser.Preference.SettingPreference
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.Response.GithubUserListItem
import com.dicoding.mygithubuser.ViewModel.MainViewModel
import com.dicoding.mygithubuser.ViewModel.SettingViewModel
import com.dicoding.mygithubuser.ViewModel.SettingViewModelFactory
import com.dicoding.mygithubuser.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
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

        val pref = SettingPreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSetting().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val moveIntent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
