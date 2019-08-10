package com.example.fourinoneapp.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapter.GalleryPicturesAdapter
import com.example.fourinoneapp.adapter.SpaceItemDecoration
import com.example.fourinoneapp.model.GalleryPicture
import com.example.fourinoneapp.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_gallery.*
import java.util.jar.Manifest

class GalleryActivity  : AppCompatActivity() {
    private lateinit var galleryViewModel : GalleryViewModel
    private lateinit var pictures: ArrayList<GalleryPicture>
    private lateinit var adapter: GalleryPicturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        requestReadStoragePermission()
    }


    private fun requestReadStoragePermission() {
        val readStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                this, readStorage
            ) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(readStorage), 3)
        }else init()
    }

    private fun init() {
        galleryViewModel = ViewModelProviders.of(this)[GalleryViewModel::class.java]
        val layoutManager = GridLayoutManager(this, 3)
        galleryRV.layoutManager = layoutManager
        galleryRV.addItemDecoration(SpaceItemDecoration(8))
        pictures = ArrayList(galleryViewModel.getGallerySize(this))
        adapter = GalleryPicturesAdapter(pictures, 10)
        galleryRV.adapter = adapter


        adapter.setOnClickListener { galleryPicture ->
            showToast(galleryPicture.path)
        }

        galleryRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == pictures.lastIndex) {
                    loadPictures(25)
                }
            }
        })
        loadPictures(25)

    }


    private fun getSelectedItemsCount() = adapter.getSelectedItems().size


    private fun loadPictures(pageSize: Int) {
        galleryViewModel.getImagesFromGallery(this, pageSize) {
            if (it.isNotEmpty()) {
                pictures.addAll(it)
                adapter.notifyItemRangeInserted(pictures.size, it.size)
            }
            Log.i("GalleryListSize", "${pictures.size}")

        }

    }

    override fun onBackPressed() {
        if (adapter.removedSelection()) {
        } else {
            super.onBackPressed()
        }


    }

    private fun showToast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            init()
        else {
            showToast("Permission Required to Fetch Gallery.")
            super.onBackPressed()
        }
    }
}