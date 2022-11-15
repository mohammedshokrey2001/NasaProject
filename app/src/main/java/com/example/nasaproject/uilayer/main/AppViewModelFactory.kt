package com.example.nasaproject.uilayer.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class AppViewModelFactory(val app: Application) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(app) as T
        }
                  throw IllegalArgumentException("Unable to construct viewmodel Main")

    }
}


