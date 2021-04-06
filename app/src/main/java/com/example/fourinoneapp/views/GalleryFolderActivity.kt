package com.example.fourinoneapp.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.ImageFolderAdapter
import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.models.ImageFolder
import com.example.fourinoneapp.models.folderFac
import com.example.fourinoneapp.models.folderToRealm
import com.example.fourinoneapp.views.utils.MarginDecoration
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_gallery_folder.*
import java.util.*
import kotlin.properties.Delegates

class GalleryFolderActivity  : AppCompatActivity() , ImageClickListener {

    private var realm: Realm by Delegates.notNull()
    private var realmConfig: RealmConfiguration by Delegates.notNull()
    private val picturePaths: ArrayList<ImageFolder>
        get() {
            var exTe : HashSet<String> = hashSetOf()
            var exTTe : HashSet<String> = hashSetOf()
            val sharedPreferences = getSharedPreferences("fFile", Context.MODE_PRIVATE)
            val tempList = sharedPreferences.getStringSet("folders",null)
            val seditor = sharedPreferences.edit()
            val dPreferences = getSharedPreferences("dFile", Context.MODE_PRIVATE)
            val hideList = dPreferences.getStringSet("hides",null)
            val deditor = dPreferences.edit()
            var index = 0

            val picFolders = ArrayList<ImageFolder>()
            val picPaths = ArrayList<String>()

            Realm.init(this)
            realmConfig = RealmConfiguration.Builder().build()
//            Realm.deleteRealm(realmConfig)
            realm = Realm.getInstance(realmConfig)

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
<<<<<<< HEAD
                    val result = realm.where(folderFac::class.java).findAll()
=======
                    if(tempList == null){
                        exTe.add(folder)
                    }
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8

                    folderpaths = "$folderpaths$folder/"
                    if (!picPaths.contains(folderpaths)) {
                        picPaths.add(folderpaths)
                        folds.path = folderpaths
                        folds.folderName = folder
                        folds.firstPic = datapath
<<<<<<< HEAD
=======

>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
                        folds.addpics()
                        picFolders.add(folds)

                        if(!result.contains(folderToRealm(folds))) {

                            realm.beginTransaction()
                            var foldFac = realm.createObject(folderFac::class.java)
                            foldFac.folderName = folds.folderName
                            foldFac.path = folds.path
                            foldFac.isSelect = true
                            foldFac.numberOfPics = 0
                            foldFac.firstPic = folds.firstPic
                            realm.commitTransaction()
                        }
                    } else {
                        for (i in picFolders.indices) {
                            if (picFolders[i].path == folderpaths) {

                                picFolders[i].firstPic = datapath
                                picFolders[i].addpics()
                            }
                        }
                    }
                } while (cursor!!.moveToNext())
                if(tempList == null) {
                    seditor.putStringSet("folders", exTe)
                    seditor.commit()
                }

                if (hideList == null) {
                    for (i in picFolders.indices) {
                        exTTe.add("true" + i)
                    }
                    deditor.putStringSet("hides", exTTe)
                    deditor.commit()
                }
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
        setContentView(R.layout.activity_gallery_folder)

        val sharedPreferences = getSharedPreferences("dFile", Context.MODE_PRIVATE)
        val trueTempList = sharedPreferences.getStringSet("hides",null)
        if (ContextCompat.checkSelfPermission(this@GalleryFolderActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this@GalleryFolderActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)

        folderRV.addItemDecoration(MarginDecoration(this))
        folderRV.hasFixedSize()
        val folds = picturePaths

        initListener()
        if (folds.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
<<<<<<< HEAD
            val folderAdapter = ImageFolderAdapter(this@GalleryFolderActivity, this,realm.where(folderFac::class.java).equalTo("isSelect",true).findAll().toList())
=======
            val folderAdapter : ImageFolderAdapter
            if(trueTempList!= null){
                folderAdapter = ImageFolderAdapter(folds, this@GalleryFolderActivity, this,trueTempList)
            }
            else{
                folderAdapter = ImageFolderAdapter(folds, this@GalleryFolderActivity, this, setOf())
            }
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
            val layoutManager = GridLayoutManager(this, 3);
            folderRV.layoutManager = layoutManager;
            folderRV.adapter = folderAdapter
        }
        initListener()
    }

    private fun initListener(){
        galleryMenuImgV.setOnClickListener{
            startActivity((Intent(this, GalleryHideActivity::class.java)))
        }
    }

    override fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>) {

    }

    override fun onPicClicked(pictureFolderPath: String, folderName: String) {
        val move = Intent(this@GalleryFolderActivity, GalleryExportActivity::class.java)
        move.putExtra("folderPath", pictureFolderPath)
        move.putExtra("folderName", folderName)
        startActivity(move)
    }

    companion object {
        private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    }
}
