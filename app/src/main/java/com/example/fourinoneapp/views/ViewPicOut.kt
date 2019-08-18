package com.example.fourinoneapp.views

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.transition.Fade
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.ImageAdapter
import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.fragments.ImageBrowseFragment
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import kotlinx.android.synthetic.main.activity_gallery_image.*
import java.util.ArrayList

class ViewPicOut  : AppCompatActivity(), ImageClickListener {

    private lateinit var folderPath: String
    private lateinit var allpictures: ArrayList<ImageFacer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_out_pic)

        folderName.text = intent.getStringExtra("folderName")

        folderPath = intent.getStringExtra("folderPath")
        allpictures = ArrayList()
        imageRV.addItemDecoration(MarginDecoration(this))
        imageRV.hasFixedSize()


        if (allpictures.isEmpty()) {
            allpictures = getAllImagesByFolder(folderPath)
            imageRV.adapter = ImageAdapter(allpictures, this, this)
        } else {

        }
    }

    override fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>) {
        val browser = ImageBrowseFragment.newInstance(pics, position, this)

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

    fun getAllImagesByFolder(path: String): ArrayList<ImageFacer> {
        var images = ArrayList<ImageFacer>()
        val allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)
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
