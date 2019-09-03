package com.example.fourinoneapp.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.models.ImageExporter
import kotlinx.android.synthetic.main.activity_gallery_export_detail.*

class GalleryExportDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_export_detail)

        Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${"Detail"}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")
        val exportImg = intent.getSerializableExtra("exportImg") as ImageExporter
        Glide.with(this)
            .load(exportImg.imageFacer.picturePath)
            .apply(RequestOptions().fitCenter())
            .into(exportImgIV)

        exportTxtTV.text = exportImg.imageTXT

//        TODO : intent에서 text받기

    }
}