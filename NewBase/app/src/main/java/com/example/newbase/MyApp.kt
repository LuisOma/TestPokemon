package com.example.newbase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.example.newbase.data.dataSource.local.DatabaseBuilder

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseBuilder.initDB(applicationContext)
    }

}