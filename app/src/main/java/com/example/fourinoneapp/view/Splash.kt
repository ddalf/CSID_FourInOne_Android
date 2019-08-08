package com.example.fourinoneapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R

class Splash  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash);
        val intent = Intent(this, GalleryActivity::class.java)            // 실제 사용할 메인 액티비티
        startActivity(intent)
        finish()

    }
}