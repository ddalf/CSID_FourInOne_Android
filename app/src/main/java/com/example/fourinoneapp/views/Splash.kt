package com.example.fourinoneapp.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R

class Splash  : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash);

        Handler().postDelayed({
            startActivity((Intent(this,GalleryActivity::class.java)))
            finish()
        }, SPLASH_TIME_OUT)
    }
}