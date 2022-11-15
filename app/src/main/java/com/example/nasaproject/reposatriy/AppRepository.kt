package com.example.nasaproject.reposatriy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.nasaproject.datalayer.api.NasaApi
import com.example.nasaproject.datalayer.api.asDatabaseModel
import com.example.nasaproject.datalayer.api.parseAsteroidsJsonResult
import com.example.nasaproject.datalayer.localdatabase.AstroidRoom
import com.example.nasaproject.datalayer.localdatabase.astroid_db.room.asDomainModel
import com.example.nasaproject.datalayer.localdatabase.picture_db.asDomainModel
import com.example.nasaproject.domain.AsteroidAppData
import com.example.nasaproject.domain.PictureOfDay
import com.example.nasaproject.utlities.Constants
import com.example.nasaproject.utlities.formatDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.math.log

class AppRepository(private val database:AstroidRoom){

    val astroids : LiveData<List<AsteroidAppData>> =
        Transformations.map(database.astroidDao.getAsteroids()){it.asDomainModel()}

    val astroidsToday :LiveData<List<AsteroidAppData>> =
        Transformations.map(database.astroidDao.getTodayAsteroids()){it.asDomainModel()}

    val astroidsWeek :LiveData<List<AsteroidAppData>> =
        Transformations.map(database.astroidDao.getWeeklyAsteroids()){it.asDomainModel()}


    val picOfTheDay :LiveData<PictureOfDay> = Transformations.map(database.pictureOfDayDao.getPictureOfDay()){
        it?.asDomainModel()
    }


    suspend fun storeDataAndRefresh(){
        withContext(Dispatchers.IO){
            val today = formatDate()
             val Response = NasaApi.retrofitServiceAs.getAsteroids(
                 apiKey = Constants.API_KEY, startDate = today, endDate = null)

             val AstroidsFromNetwoek = parseAsteroidsJsonResult(JSONObject(Response))

            database.astroidDao.insertAll(*AstroidsFromNetwoek.asDatabaseModel())

          //  Log.i("Montior_Data", "storeDataAndRefresh: ${database.astroidDao.getAsteroids()}")
        }
    }

    suspend fun PictureOfTheDayRefreshment(){
        withContext(Dispatchers.IO){
            val pictureOfDay = NasaApi.retrofitMoshiPic.getPictureOfDay(apiKey = Constants.API_KEY)


        database.pictureOfDayDao.insertPictureOfDay(pictureOfDay =pictureOfDay.asDatabaseModel() )



    }
    }
    suspend fun clearOldAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val today = formatDate()
                database.astroidDao.clearOldAsteroids(today)
            } catch (e: Exception) {

                Log.i("Work", "clearOldAsteroids: ${e.toString()}")
            }
        }
    }

    suspend fun clearOldPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val today = formatDate()
                database.pictureOfDayDao.clearOldPictureOfDay(today)
            } catch (e: Exception) {
                Log.i("Work", "clearOldPicture: ${e.toString()}")

            }
        }
    }




}