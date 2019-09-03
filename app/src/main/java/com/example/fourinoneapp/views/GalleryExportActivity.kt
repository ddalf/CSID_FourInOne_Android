package com.example.fourinoneapp.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.ImageExportAdapter
import com.example.fourinoneapp.models.ImageExporter
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.acitivity_gallery_export.*
import java.util.*

class GalleryExportActivity : AppCompatActivity() {
//  /storage/emulated/0/DCIM/Camera/
//  /storage/6335-3934/DCIM/Camera/
private lateinit var foldePath: String

    private lateinit var allpictures: ArrayList<ImageFacer>
    private lateinit var allPicturesWithTxt : ArrayList<ImageExporter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fourinoneapp.R.layout.acitivity_gallery_export)

        foldername.text = intent.getStringExtra("folderName")
        foldePath = intent.getStringExtra("folderPath")

        Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${"Export"}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")

//        TODO : DELETE LGO
        Log.d("folderPath", foldePath)

        allpictures = ArrayList()
        exportRV.addItemDecoration(MarginDecoration(this))
        exportRV.hasFixedSize()

        if (allpictures.isEmpty()) {
            loader.visibility = View.VISIBLE
            val layoutManager = GridLayoutManager(this, 2)
            allPicturesWithTxt = getAllImagesByFolder(foldePath)

            exportRV.layoutManager = layoutManager
            exportRV.adapter = ImageExportAdapter(allPicturesWithTxt, this@GalleryExportActivity)
            loader.visibility = View.GONE
        } else {

        }
        initListener()
    }


    private fun initListener(){
        galleryMenuImgV.setOnClickListener{
            startActivity((Intent(this,GalleryHideActivity::class.java)))
        }
    }

//    override fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>) {
//        val browser = ImageBrowseFragment.newInstance(pics, position, this@GalleryImageActivity)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            browser.enterTransition = Fade()
//            browser.exitTransition = Fade()
//        }
//
//        supportFragmentManager
//            .beginTransaction()
//            .addSharedElement(holder.picture, position.toString() + "picture")
//            .add(R.id.displayContainer, browser)
//            .addToBackStack(null)
//            .commit()
//
//    }
//
//    override fun onPicClicked(pictureFolderPath: String, folderName: String) {
//
//    }

//    override fun onExportPicClicked(position: Int) {
//        val move = Intent(this@GalleryExportActivity, GalleryExportDetailActivity::class.java)
//        move.putExtra("image", allPicturesWithTxt[position])
//        startActivity(move)
//    }

    //TODO: ADD Fragment
//    fun onExportItemClicked(holder: ImageExportHolder, position: int , exports:ArrayList<ImageExporter>){
//        val browser = ImageBrowseFragment.newInstance(exports, position, this@GalleryExportActivity)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            browser.enterTransition = Fade()
//            browser.exitTransition = Fade()
//        }
//
//        supportFragmentManager
//            .beginTransaction()
//            .addSharedElement(holder.exportImgIV, position.toString() + "picture")
//            .add(R.id.exportLayout,browser)
//            .addToBackStack(null)
//            .commit()
//    }

    fun getTextFromImage(): String{
        var outString: String = ""
        val bitmap: Bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.logo1)
        val textRecognizer : TextRecognizer = TextRecognizer.Builder(applicationContext).build()

        if(!textRecognizer.isOperational){
            outString = "추출된 글씨가 없습니다"
        }else{
            val frame : Frame = Frame.Builder().setBitmap(bitmap).build()
            var items : SparseArray<TextBlock> = textRecognizer.detect(frame)
            var sb : StringBuilder = java.lang.StringBuilder()
            for(i in 0..items.size()){
                var myItem : TextBlock = items.valueAt(i)
                sb.append(myItem.value)
                sb.append("\n")
            }
            outString = sb.toString()
        }
        return outString
    }


    fun getAllImagesByFolder(path: String): ArrayList<ImageExporter> {
        var exports = ArrayList<ImageExporter>()
        val allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this@GalleryExportActivity.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)

        val language = "kor"
//        val language = "kor+eng"
        try {
            cursor!!.moveToFirst()
            do {
                val exporter = ImageExporter()
                var outString = ""
                exporter.imageFacer.picturName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                exporter.imageFacer.picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                exporter.imageFacer.pictureSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))

                exporter.imageFacer.picExt = if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".png")) "png"
                else if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".jpg")) "jpg"
                else if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".gif")) "gif"
                else "?"

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(exporter.imageFacer.picturePath, options)
                val textRecognizer : TextRecognizer = TextRecognizer.Builder(applicationContext).build()

                if(!textRecognizer.isOperational){
                    outString = "추출된 글씨가 없습니다"
                }else{
                    val frame : Frame = Frame.Builder().setBitmap(bitmap).build()
                    var items : SparseArray<TextBlock> = textRecognizer.detect(frame)
                    var sb : StringBuilder = java.lang.StringBuilder()
                    Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${items.size()}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")
                    for(i in 0..items.size()-1){
                        var myItem : TextBlock = items.valueAt(i)
                        sb.append(myItem.value)
                        sb.append("\n")
                    }
                    outString = sb.toString()
                }
                Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${exporter}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")
                exporter.imageFacer.picExt = outString
                exporter.imageTXT = exporter.imageFacer.picExt
//              TODO : ocr 내보내진 텍스트 추가하기

                exporter.imageTXT = ""

                exports.add(exporter)
                Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${exporter}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")

            } while (cursor.moveToNext())
            cursor.close()
            val reSelection = ArrayList<ImageExporter>()
            for (i in exports.size - 1 downTo -1 + 1) {
                reSelection.add(exports[i])
            }
            exports = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        exports.get(0).imageTXT = "네오젠 더마로지 \n" +
                "\n" +
                "바이오 필 거즈 필링 \n" +
                "\n" +
                "neogen dermalogy bio peel gauze peeling \n" +
                "\n" +
                "크근 니크 \n" +
                "\n" +
                "모이스춰 써지 하이드레이팅 로션 \n" +
                "\n" +
                "clinique moisture surge hydrating lotion \n" +
                "\n" +
                "웰라쥬 \n" +
                "\n" +
                "리얼 히알루로닉 원데이키트 \n" +
                "\n" +
                "wellage real hyaluronic one day kit "
        return exports
    }

}