package com.example.fourinoneapp.views

<<<<<<< HEAD
=======
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.AlbumHiderAdapter
import com.example.fourinoneapp.models.ImageFolder
import com.example.fourinoneapp.models.folderFac
import com.example.fourinoneapp.views.utils.MarginDecoration
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_hide_album.*
<<<<<<< HEAD
import java.util.*
import kotlin.properties.Delegates
=======
import java.util.ArrayList
import java.util.HashSet
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8

class GalleryHideActivity  : AppCompatActivity() {

    private var realm: Realm by Delegates.notNull()
    private var realmConfig: RealmConfiguration by Delegates.notNull()

    private val picturePaths: ArrayList<ImageFolder>
        get() {
<<<<<<< HEAD
            Realm.init(this)
            realmConfig = RealmConfiguration.Builder().build()
//            Realm.deleteRealm(realmConfig)
            realm = Realm.getInstance(realmConfig)
=======
>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8

            val picFolders = ArrayList<ImageFolder>()
            val picPaths = ArrayList<String>()
            val allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID)
            val cursor = this.contentResolver.query(allImagesuri, projection, null, null, null)
            try {
                cursor?.moveToFirst()
                do {
                    val folds = ImageFolder()
                    val name = cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                    val datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    var folderpaths = datapath.substring(0, datapath.lastIndexOf("$folder/"))
                    folderpaths = "$folderpaths$folder/"
                    if (!picPaths.contains(folderpaths)) {
                        picPaths.add(folderpaths)

                        folds.path = folderpaths
                        folds.folderName = folder
                        folds.firstPic = datapath
                        folds.addpics()
                        picFolders.add(folds)
                    } else {
                        for (i in picFolders.indices) {
                            if (picFolders[i].path == folderpaths) {
                                picFolders[i].firstPic = datapath
                                picFolders[i].addpics()
                            }
                        }
                    }
                } while (cursor!!.moveToNext())
                cursor.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            for (i in picFolders.indices) {
                Log.d("picture folders", picFolders[i].folderName + " and path = " + picFolders[i].path + " " + picFolders[i].numberOfPics)
            }
            return picFolders
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hide_album)

        val dPreferences = getSharedPreferences("dFile", Context.MODE_PRIVATE)
        val hideList = dPreferences.getStringSet("hides",null)

        val deditor = dPreferences.edit()

        var color :HashSet<Int> = hashSetOf()
        HideAlbumRV.addItemDecoration(MarginDecoration(this))
        HideAlbumRV.hasFixedSize()
        val folds = picturePaths
        Log.d("folds", folds.toString())
        val result :List<folderFac> = realm.where(folderFac::class.java).equalTo("isSelect",true).findAll().toList()

        if (folds.isEmpty()) {
        } else {
<<<<<<< HEAD
            val hiderAdapter = AlbumHiderAdapter(this@GalleryHideActivity,result)
=======
            val sharedPreferences = getSharedPreferences("dFile", Context.MODE_PRIVATE)
            val tempList = sharedPreferences.getStringSet("hides",null)
            var hiderAdapter : AlbumHiderAdapter
            if(hideList != null){
                for(i in 0..hideList.size){
                    if(hideList.elementAt(i).subSequence(0,4).equals("true")){
                        color.add(i)
                    }
                }
            }
            if(tempList == null){
                hiderAdapter = AlbumHiderAdapter(folds, this@GalleryHideActivity, hashSetOf())
            }else{
                hiderAdapter = AlbumHiderAdapter(folds, this@GalleryHideActivity,color)
            }

>>>>>>> d8758a52cfd418e8a00b0ea01f8a054e766bd5f8

            val layoutManager = GridLayoutManager(this, 1);
            HideAlbumRV.layoutManager = layoutManager;
            HideAlbumRV.adapter = hiderAdapter
        }
    }
}