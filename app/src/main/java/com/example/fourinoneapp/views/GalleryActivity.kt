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
import androidx.recyclerview.widget.RecyclerView
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.PictureFolderAdapter
import com.example.fourinoneapp.adapters.viewholders.PicHolder
import com.example.fourinoneapp.listeners.itemClickListener
import com.example.fourinoneapp.viewmodels.imageFolder
import com.example.fourinoneapp.viewmodels.pictureFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import java.util.ArrayList

class GalleryActivity  : AppCompatActivity() , itemClickListener {

    private lateinit var folderRecycler: RecyclerView
    private lateinit var empty: TextView

    private val picturePaths: ArrayList<imageFolder>
        get() {
            val picFolders = ArrayList<imageFolder>()
            val picPaths = ArrayList<String>()
            val allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID)
            val cursor = this.contentResolver.query(allImagesuri, projection, null, null, null)
            try {
                cursor?.moveToFirst()
                do {
                    val folds = imageFolder()
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
        setContentView(R.layout.activity_gallery)

        if (ContextCompat.checkSelfPermission(this@GalleryActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this@GalleryActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        //____________________________________________________________________________________

        empty = findViewById(R.id.empty)

        folderRecycler = findViewById(R.id.folderRecycler)
        folderRecycler.addItemDecoration(MarginDecoration(this))
        folderRecycler.hasFixedSize()
        val folds = picturePaths

        if (folds.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
            val folderAdapter = PictureFolderAdapter(folds, this@GalleryActivity, this)
            folderRecycler.adapter = folderAdapter
        }
    }


    override fun onPicClicked(holder: PicHolder, position: Int, pics: ArrayList<pictureFacer>) {

    }

    override fun onPicClicked(pictureFolderPath: String, folderName: String) {
        val move = Intent(this@GalleryActivity, ImageDisplayActivity::class.java)
        move.putExtra("folderPath", pictureFolderPath)
        move.putExtra("folderName", folderName)
        startActivity(move)
    }

    companion object {
        private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    }
}