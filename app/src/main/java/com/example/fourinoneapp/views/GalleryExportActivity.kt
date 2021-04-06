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
<<<<<<< HEAD
import kotlin.properties.Delegates
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

=======
>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import kotlin.collections.HashSet
<<<<<<< HEAD

=======
import kotlin.collections.ArrayList
>>>>>>> 85f513a41c6d44ba68aaee4f9362e4a64a5efec2
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8

=======
import kotlin.collections.ArrayList
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8
>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
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
<<<<<<< HEAD
<<<<<<< HEAD
            allPicturesWithTxt = getAllImagesByFolder(foldePath)
=======
            allPicturesWithTxt = ArrayList()
            allPicturesWithTxt.addAll(getAllImagesByFolder(foldePath))

>>>>>>> 85f513a41c6d44ba68aaee4f9362e4a64a5efec2
=======
            allPicturesWithTxt = getAllImagesByFolder(foldePath)
>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
            searchViewModel.originalImages.addAll(allPicturesWithTxt)
            searchViewModel.oldfilteredImages.addAll(allPicturesWithTxt)

            exportRV.layoutManager = layoutManager
            exportRV.adapter = ImageExportAdapter(searchViewModel.oldfilteredImages, this@GalleryExportActivity)
            loader.visibility = View.GONE
        } else {

        }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD



=======
=======

>>>>>>> 8d2517b... reset
=======

>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91
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
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 6b0a4c1... change layout
    }

<<<<<<< HEAD

=======
    }

>>>>>>> 5e17745... Change search et
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
=======
=======
    }

>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
<<<<<<< HEAD
>>>>>>> 66dbbeb... Add search function
=======
>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91


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

<<<<<<< HEAD

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

=======

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

>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
                        var exporter = realm.createObject(Exporter::class.java)
                        exporter.picturName =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        exporter.picturePath =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                        exporter.imageUri = ""
                        exporter.imageTXT = outString

<<<<<<< HEAD
                        realm.commitTransaction()
                        exports.add(ImageExporter(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),"",outString))
                    }



                } while (cursor.moveToNext())
                cursor.close()

            } catch (e: Exception) {
                e.printStackTrace()
=======
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
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91
                exporter.imageFacer.picExt = if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".png")) "png"
                else if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".jpg")) "jpg"
                else if(exporter.imageFacer.picturName.toString().toLowerCase().endsWith(".gif")) "gif"
                else "?"
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
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
<<<<<<< HEAD
=======

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(exporter.imageFacer.picturePath, options)
                val textRecognizer : TextRecognizer = TextRecognizer.Builder(applicationContext).build()

                if(!textRecognizer.isOperational){
                    outString = ""
                    exporter.imageTXT = outString
                }else{
                    val frame : Frame = Frame.Builder().setBitmap(bitmap).build()
                    var items : SparseArray<TextBlock> = textRecognizer.detect(frame)
                    var sb : StringBuilder = java.lang.StringBuilder()
                    Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${items.size()}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")
                    for(i in 0..items.size()-1){
                        var myItem : TextBlock = items.valueAt(i)
                        sb.append(myItem.value)
                        sb.append("\n")
>>>>>>> 85f513a41c6d44ba68aaee4f9362e4a64a5efec2
=======

>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
                    }
                    exporter.imageFacer.picExt = outString
                }else{
                    exporter.imageFacer.picExt = tempList.elementAt(index++)
                }
                exporter.imageTXT = exporter.imageFacer.picExt
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
//              TODO : ocr 내보내진 텍스트 추가하기

                exporter.imageTXT = ""
>>>>>>> 6b0a4c1... change layout
=======
>>>>>>> 5e17745... Change search et

=======
>>>>>>> 8d2517b... reset
=======
>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91
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
<<<<<<< HEAD
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
=======
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8
>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
            }
        }

<<<<<<< HEAD
        val reSelection = ArrayList<ImageExporter>()
        for (i in exports.size - 1 downTo -1 + 1) {
            reSelection.add(exports[i])
        }
        exports = reSelection
=======

<<<<<<< HEAD
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
=======
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8
>>>>>>> 023cf0e3688fd509a73dbd63325d2ab391e7b7b8
        return exports
    }

}