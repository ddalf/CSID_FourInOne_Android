package com.example.fourinoneapp.views

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.transition.Fade
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.adapters.viewholders.ImageAdapter
import com.example.fourinoneapp.fragments.ImageBrowseFragment
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import kotlinx.android.synthetic.main.activity_gallery_image.*
import java.util.ArrayList

class GalleryImageActivity : AppCompatActivity(), ImageClickListener {

    private lateinit var foldePath: String
    private lateinit var allpictures: ArrayList<ImageFacer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_image)

        folderName.text = intent.getStringExtra("folderName")

        foldePath = intent.getStringExtra("folderPath")
        allpictures = ArrayList()
        imageRV.addItemDecoration(MarginDecoration(this))
        imageRV.hasFixedSize()


        if (allpictures.isEmpty()) {
            loader.visibility = View.VISIBLE
            val layoutManager = GridLayoutManager(this, 3);
            allpictures = getAllImagesByFolder(foldePath)
            imageRV.layoutManager = layoutManager;
            imageRV.adapter = ImageAdapter(allpictures, this@GalleryImageActivity, this)
            loader.visibility = View.GONE
        } else {

        }
        initListener()
    }

    override fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>) {
        val browser = ImageBrowseFragment.newInstance(pics, position, this@GalleryImageActivity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            browser.enterTransition = Fade()
            browser.exitTransition = Fade()
        }

        supportFragmentManager
            .beginTransaction()
            .addSharedElement(holder.picture, position.toString() + "picture")
            .add(R.id.displayContainer, browser)
            .addToBackStack(null)
            .commit()

    }

    override fun onPicClicked(pictureFolderPath: String, folderName: String) {

    }

    private fun initListener(){
        gallerySearchImgV.setOnClickListener{
            if(searchET.visibility != View.VISIBLE){
                folderName.visibility = View.INVISIBLE
                searchET.visibility = View.VISIBLE
            }
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

    fun getAllImagesByFolder(path: String): ArrayList<ImageFacer> {
        var images = ArrayList<ImageFacer>()
        val allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this@GalleryImageActivity.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)
        try {
            cursor!!.moveToFirst()
            do {
                val pic = ImageFacer()

                pic.picturName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                pic.picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                pic.pictureSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))

                images.add(pic)
            } while (cursor.moveToNext())
            cursor.close()
            val reSelection = ArrayList<ImageFacer>()
            for (i in images.size - 1 downTo -1 + 1) {
                reSelection.add(images[i])
            }
            images = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }
}
