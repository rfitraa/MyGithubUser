package com.dicoding.mygithubuser.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuser.Preference.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreference): ViewModel() {
    fun getThemeSetting() : LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}