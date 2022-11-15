package com.example.nasaproject.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nasaproject.datalayer.localdatabase.getDatabase
import com.example.nasaproject.reposatriy.AppRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AppRepository(database)
        return try {
            repository.storeDataAndRefresh()
            repository.PictureOfTheDayRefreshment()
            repository.clearOldAsteroids()
            repository.clearOldPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}