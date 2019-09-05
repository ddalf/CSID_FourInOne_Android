package com.example.fourinoneapp.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.adapters.ImageExportAdapter
import com.example.fourinoneapp.models.ImageExporter
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.viewmodels.ImageViewModel
import com.example.fourinoneapp.views.utils.ImagesDiffUtilCallback
import com.example.fourinoneapp.views.utils.MarginDecoration
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.acitivity_gallery_export.*
import java.util.*
import java.util.concurrent.TimeUnit
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import kotlin.collections.HashSet
import kotlin.collections.ArrayList
class GalleryExportActivity : AppCompatActivity() {
private lateinit var foldePath: String

    private lateinit var allpictures: ArrayList<ImageFacer>
    private lateinit var allPicturesWithTxt : ArrayList<ImageExporter>
    private lateinit var searchViewModel : ImageViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fourinoneapp.R.layout.acitivity_gallery_export)

        searchViewModel = ViewModelProviders.of(this).get(ImageViewModel::class.java)



        foldername.text = intent.getStringExtra("folderName")
        foldePath = intent.getStringExtra("folderPath")

        Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${"Export"}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")

        allpictures = ArrayList()
        exportRV.addItemDecoration(MarginDecoration(this))
        exportRV.hasFixedSize()

        if (allpictures.isEmpty()) {
            loader.visibility = View.VISIBLE
            val layoutManager = GridLayoutManager(this, 2)
            allPicturesWithTxt = getAllImagesByFolder(foldePath)
            searchViewModel.originalImages.addAll(allPicturesWithTxt)
            searchViewModel.oldfilteredImages.addAll(allPicturesWithTxt)

            exportRV.layoutManager = layoutManager
            exportRV.adapter = ImageExportAdapter(searchViewModel.oldfilteredImages, this@GalleryExportActivity)
            loader.visibility = View.GONE
        } else {

        }

        initListener()
    }


    private fun initListener(){
        gallerySearchImgV.setOnClickListener{
            if(searchET.visibility != View.VISIBLE){
                foldername.visibility = View.INVISIBLE
                searchET.visibility = View.VISIBLE
            }
        }

        searchET
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe {
                searchViewModel
                    .search(it.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val diffResult = DiffUtil.calculateDiff(ImagesDiffUtilCallback(searchViewModel.oldfilteredImages, searchViewModel.filterdImages))
                        searchViewModel.oldfilteredImages.clear()
                        searchViewModel.oldfilteredImages.addAll(searchViewModel.filterdImages)
                        if(searchViewModel.filterdImages.size == 0){
                            empty.visibility = View.VISIBLE
                        }
                        else{
                            empty.visibility = View.GONE
                        }
                        diffResult.dispatchUpdatesTo(exportRV.adapter!!)
                    }.addTo(disposable)
            }.addTo(disposable)
    }

    override fun onBackPressed() {
        if(searchET.visibility == View.VISIBLE){
            foldername.visibility = View.VISIBLE
            searchET.text = null
            searchET.visibility = View.INVISIBLE
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }


//    fun getTextFromImage(): String{
//        var outString: String = ""
//        val bitmap: Bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.logo1)
//        val textRecognizer : TextRecognizer = TextRecognizer.Builder(applicationContext).build()
//
//        if(!textRecognizer.isOperational){
//            outString = "추출된 글씨가 없습니다"
//        }else{
//            val frame : Frame = Frame.Builder().setBitmap(bitmap).build()
//            var items : SparseArray<TextBlock> = textRecognizer.detect(frame)
//            var sb : StringBuilder = java.lang.StringBuilder()
//            for(i in 0..items.size()){
//                var myItem : TextBlock = items.valueAt(i)
//                sb.append(myItem.value)
//                sb.append("\n")
//            }
//            outString = sb.toString()
//        }
//        return outString
//    }


    private fun getAllImagesByFolder(path: String): ArrayList<ImageExporter> {
        var exports = ArrayList<ImageExporter>()
        val allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this@GalleryExportActivity.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)

        var exTe : HashSet<String> = hashSetOf()
        val sharedPreferences = getSharedPreferences("sFile", Context.MODE_PRIVATE)
        val tempList = sharedPreferences.getStringSet("images",null)
        val editor = sharedPreferences.edit()
        var index = 0
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

                Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${tempList?.size}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")

                if(tempList == null) {
                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
                    val bitmap = BitmapFactory.decodeFile(exporter.imageFacer.picturePath, options)
                    val textRecognizer: TextRecognizer =
                        TextRecognizer.Builder(applicationContext).build()

                    if (!textRecognizer.isOperational) {
                        outString = "추출된 글씨가 없습니다"
                        exporter.imageTXT = outString
                    } else {
                        val frame: Frame = Frame.Builder().setBitmap(bitmap).build()
                        var items: SparseArray<TextBlock> = textRecognizer.detect(frame)
                        var sb: StringBuilder = java.lang.StringBuilder()
                        for (i in 0..items.size() - 1) {
                            var myItem: TextBlock = items.valueAt(i)
                            sb.append(myItem.value)
                            sb.append("\n")
                        }
                        outString = sb.toString()
                        exTe.add(outString)

                    }
                    exporter.imageFacer.picExt = outString
                }else{
                    exporter.imageFacer.picExt = tempList.elementAt(index++)
                }
                exporter.imageTXT = exporter.imageFacer.picExt
                exports.add(exporter)

            } while (cursor.moveToNext())
            if(tempList == null) {
                editor.putStringSet("images", exTe)
                editor.commit()
            }
            cursor.close()
            val reSelection = ArrayList<ImageExporter>()
            for (i in exports.size - 1 downTo -1 + 1) {
                reSelection.add(exports[i])
            }
            exports = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return exports
    }

}