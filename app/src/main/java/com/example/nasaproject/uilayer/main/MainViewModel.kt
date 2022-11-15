package com.example.nasaproject.uilayer.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.nasaproject.datalayer.localdatabase.getDatabase
import com.example.nasaproject.domain.AsteroidAppData
import com.example.nasaproject.reposatriy.AppRepository
import com.example.nasaproject.utlities.Constants
import com.example.nasaproject.utlities.isNetworkAvailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.log

class MainViewModel(application: Application) : AndroidViewModel(application) {

  var AppRepo = AppRepository(getDatabase(application) )


    init {


        viewModelScope.launch {
            try {

                if (isNetworkAvailable(getApplication<Application?>().applicationContext)) {
                    AppRepo.storeDataAndRefresh()
                    AppRepo.PictureOfTheDayRefreshment()

                } else {
                    Log.i("Network_Avilabilty", ": none")
                }


            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }

        }
    }

    private val menuSelection =MutableLiveData<Int>(Constants.SAVED_CONST)


    @SuppressLint("SuspiciousIndentation")
    fun updateSelection(selection :Int) :LiveData<List<AsteroidAppData>> {
     menuSelection.value = selection
        when(menuSelection.value){
            Constants.WEEK_CONST -> return AppRepo.astroidsWeek
            Constants.SAVED_CONST ->return AppRepo.astroids
            Constants.TODAY_CONST -> return AppRepo.astroidsToday
        }

         return AppRepo.astroids
    }



    }



