package com.example.fourinoneapp.views

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
import com.example.fourinoneapp.models.Exporter
import com.example.fourinoneapp.models.ImageExporter
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.models.exportToRealm
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
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.acitivity_gallery_export.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class GalleryExportActivity : AppCompatActivity() {
    private lateinit var foldePath: String

    private lateinit var allpictures: ArrayList<ImageFacer>
    private lateinit var allPicturesWithTxt : ArrayList<ImageExporter>
    private lateinit var searchViewModel : ImageViewModel
    private val disposable = CompositeDisposable()
    private var realm: Realm by Delegates.notNull()
    private var realmConfig: RealmConfiguration by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fourinoneapp.R.layout.acitivity_gallery_export)


        Realm.init(this)


        realmConfig = RealmConfiguration.Builder().build()
//        Realm.deleteRealm(realmConfig)
        realm = Realm.getInstance(realmConfig)



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
            allPicturesWithTxt = ArrayList()
            allPicturesWithTxt.addAll(getAllImagesByFolder(foldePath))

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
//        val result = realm.where(Exporter::class.java).equalTo("picturePath","").findAll()
        val result = realm.where(Exporter::class.java).like("picturePath",foldePath+"*").findAll()
        Log.d(
            "tess",
            "\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${result.size}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A"
        )
        Log.d(
            "tess",
            "\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${foldePath}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A"
        )

        if(result.size != 0) {
            result.forEach {
                exports.add(ImageExporter(it.picturName,it.picturePath,it.imageUri,it.imageTXT))
            }
        }
        else{
            val language = "kor"
            //        val language = "kor+eng"
            try {
                cursor!!.moveToFirst()
                do {
                    var outString = ""


                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
                    val bitmap = BitmapFactory.decodeFile(
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        ), options
                    )
                    val textRecognizer: TextRecognizer =
                        TextRecognizer.Builder(applicationContext).build()
                    val result = realm.where(Exporter::class.java).findAll()
                    if (!textRecognizer.isOperational) {
                        outString = ""
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
                    }

                    if(!result.contains(exportToRealm(ImageExporter(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),"",outString)))){
                        realm.beginTransaction()

                        var exporter = realm.createObject(Exporter::class.java)
                        exporter.picturName =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        exporter.picturePath =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                        exporter.imageUri = ""
                        exporter.imageTXT = outString

                        realm.commitTransaction()
                        exports.add(ImageExporter(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),"",outString))
                    }



                } while (cursor.moveToNext())
                cursor.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val reSelection = ArrayList<ImageExporter>()
        for (i in exports.size - 1 downTo -1 + 1) {
            reSelection.add(exports[i])
        }
        exports = reSelection
        return exports
    }

}