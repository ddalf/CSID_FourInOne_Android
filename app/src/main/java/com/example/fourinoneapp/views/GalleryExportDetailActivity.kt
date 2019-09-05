package com.example.fourinoneapp.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.models.ImageExporter
import kotlinx.android.synthetic.main.activity_gallery_export_detail.*
import android.widget.TextView
import android.content.ClipData
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.view.View.OnLongClickListener
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast


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

        if(exportImg.imageTXT.equals("")||exportImg.imageTXT == null){
            val noTxtDrawable = this.getDrawable(R.drawable.rounded_no_txt)
            exportTxtCV.background = noTxtDrawable
        }
        exportTxtTV.text = exportImg.imageTXT
        initListener()
    }

    private fun initListener(){
        exportTxtCopy.setOnClickListener{
            val cm = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("text", exportTxtTV.text))
            if(!exportTxtTV.text.equals("")){
                Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}