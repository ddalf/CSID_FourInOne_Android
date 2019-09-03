package com.example.fourinoneapp.views

import android.os.Bundle
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

        val exportImg = intent.getSerializableExtra("exportImg") as ImageExporter
        Glide.with(this)
            .load(exportImg.imageFacer.picturePath)
            .apply(RequestOptions().fitCenter())
            .into(exportImgIV)

        exportTxtTV.text = exportImg.imageTXT

//        TODO : intent에서 text받기

    }
}