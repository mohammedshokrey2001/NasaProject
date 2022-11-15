package com.example.nasaproject

import android.app.Application
import android.os.Build
import androidx.work.*
import com.example.nasaproject.work_manager.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplicationEntry : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRefreshRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "work_refresh",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRefreshRequest
        )

        val repeatingDeleteRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "work_delete",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingDeleteRequest
        )
    }
}