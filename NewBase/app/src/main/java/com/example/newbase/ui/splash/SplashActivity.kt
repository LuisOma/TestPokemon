package com.example.newbase.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newbase.R
import com.example.newbase.data.dataSource.local.DatabaseBuilder
import com.example.newbase.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity<MainActivity>()
    }
}
