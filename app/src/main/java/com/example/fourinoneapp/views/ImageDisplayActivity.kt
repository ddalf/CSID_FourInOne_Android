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
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.PicHolder
import com.example.fourinoneapp.adapters.viewholders.PictureAdapter
import com.example.fourinoneapp.fragments.pictureBrowserFragment
import com.example.fourinoneapp.listeners.itemClickListener
import com.example.fourinoneapp.viewmodels.pictureFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import java.util.ArrayList

/**
 * Author CodeBoy722
 *
 * This Activity get a path to a folder that contains images from the MainActivity Intent and displays
 * all the images in the folder inside a RecyclerView
 */

class ImageDisplayActivity : AppCompatActivity(), itemClickListener {

    private lateinit var imageRecycler: RecyclerView
    private lateinit var allpictures: ArrayList<pictureFacer>
    private lateinit var load: ProgressBar
    private lateinit var foldePath: String
    private lateinit var folderName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        folderName = findViewById(R.id.foldername)
        folderName.text = intent.getStringExtra("folderName")

        foldePath = intent.getStringExtra("folderPath")
        allpictures = ArrayList()
        imageRecycler = findViewById(R.id.imageRV)
        imageRecycler.addItemDecoration(MarginDecoration(this))
        imageRecycler.hasFixedSize()
        load = findViewById(R.id.loader)


        if (allpictures.isEmpty()) {
            load.visibility = View.VISIBLE
            allpictures = getAllImagesByFolder(foldePath)
            imageRecycler.adapter = PictureAdapter(allpictures, this@ImageDisplayActivity, this)
            load.visibility = View.GONE
        } else {

        }
    }

    /**
     *
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics An ArrayList of all the items in the Adapter
     */
    override fun onPicClicked(holder: PicHolder, position: Int, pics: ArrayList<pictureFacer>) {
        val browser = pictureBrowserFragment.newInstance(pics, position, this@ImageDisplayActivity)

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //browser.setEnterTransition(new Slide());
            //browser.setExitTransition(new Slide()); uncomment this to use slide transition and comment the two lines below
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

    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */
    fun getAllImagesByFolder(path: String): ArrayList<pictureFacer> {
        var images = ArrayList<pictureFacer>()
        val allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this@ImageDisplayActivity.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)
        try {
            cursor!!.moveToFirst()
            do {
                val pic = pictureFacer()

                pic.picturName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                pic.picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                pic.pictureSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))

                images.add(pic)
            } while (cursor.moveToNext())
            cursor.close()
            val reSelection = ArrayList<pictureFacer>()
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
