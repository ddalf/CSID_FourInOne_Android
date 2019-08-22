package com.example.fourinoneapp.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.ImageFolderAdapter
import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFolder
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import kotlinx.android.synthetic.main.activity_gallery_folder.*
import kotlinx.android.synthetic.main.activity_gallery_folder.gallerySearchImgV
import kotlinx.android.synthetic.main.picture_folder_item.*
import kotlinx.android.synthetic.main.picture_folder_item.folderName
import java.util.ArrayList

class GalleryFolderActivity  : AppCompatActivity() , ImageClickListener {

    private val picturePaths: ArrayList<ImageFolder>
        get() {
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
        setContentView(R.layout.activity_gallery_folder)

        if (ContextCompat.checkSelfPermission(this@GalleryFolderActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this@GalleryFolderActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)

        folderRV.addItemDecoration(MarginDecoration(this))
        folderRV.hasFixedSize()
        val folds = picturePaths

        if (folds.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
            val folderAdapter = ImageFolderAdapter(folds, this@GalleryFolderActivity, this)
            val layoutManager = GridLayoutManager(this, 3);
            folderRV.layoutManager = layoutManager;
            folderRV.adapter = folderAdapter
        }

        initListener()
    }

    private fun initListener(){
        gallerySearchImgV.setOnClickListener{
            if(searchET.visibility != View.VISIBLE){
                folderName.visibility = View.INVISIBLE
                searchET.visibility = View.VISIBLE
            }
        }
        galleryMenuImgV.setOnClickListener{
            startActivity((Intent(this,GalleryHideActivity::class.java)))
        }
    }

    override fun onBackPressed() {
        if(searchET.visibility == View.VISIBLE){
            folderName.visibility = View.VISIBLE
            searchET.visibility = View.INVISIBLE
        }
        else{
            super.onBackPressed()
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