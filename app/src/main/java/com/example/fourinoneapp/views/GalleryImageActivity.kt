package com.example.fourinoneapp.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.ImageAdapter
import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFacer
import com.example.fourinoneapp.views.utils.MarginDecoration
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_gallery_image.*
import java.util.*

class GalleryImageActivity : AppCompatActivity(), ImageClickListener {

    private lateinit var foldePath: String
    private lateinit var allpictures: ArrayList<ImageFacer>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_image)



        folderName.text = intent.getStringExtra("folderName")
        foldePath = intent.getStringExtra("folderPath")

        Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${"Image"}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")

//        TODO : DELETE LGO
        Log.d("folderName", folderName.text.toString())
        Log.d("folderPath", foldePath)


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
    }

    override fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>) {
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
    }

    override fun onPicClicked(pictureFolderPath: String, folderName: String) {

    }

    private fun initListener() {
        gallerySearchImgV.setOnClickListener{
            Toast.makeText(this, "click", Toast.LENGTH_LONG).show()
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

    fun getAllImagesByFolder(path: String): ArrayList<ImageFacer> {
        var images = ArrayList<ImageFacer>()
        val allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE)
        val cursor = this@GalleryImageActivity.contentResolver.query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf("%$path%"), null)
        try {
            cursor!!.moveToFirst()
            do {
                val pic = ImageFacer()
                var outString = ""

                pic.picturName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                pic.picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                pic.pictureSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                pic.picExt = if(pic.picturName.toString().toLowerCase().endsWith(".png")) "png"
                else if(pic.picturName.toString().toLowerCase().endsWith(".jpg")) "jpg"
                else if(pic.picturName.toString().toLowerCase().endsWith(".gif")) "gif"
                else "?"

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(pic.picturePath, options)
                val textRecognizer : TextRecognizer = TextRecognizer.Builder(applicationContext).build()

                if(!textRecognizer.isOperational){
                    outString = "추출된 글씨가 없습니다"
                }else{
                    val frame : Frame = Frame.Builder().setBitmap(bitmap).build()
                    var items : SparseArray<TextBlock> = textRecognizer.detect(frame)
                    var sb : StringBuilder = java.lang.StringBuilder()
                    Log.d("tess","\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A${items.size()}\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE4A")
                    for(i in 0..items.size()-1){
                        var myItem : TextBlock = items.valueAt(i)
                        sb.append(myItem.value)
                        sb.append("\n")
                    }
                    outString = sb.toString()
                }
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
