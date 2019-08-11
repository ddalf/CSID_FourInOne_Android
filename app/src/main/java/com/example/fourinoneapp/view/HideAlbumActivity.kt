package com.example.fourinoneapp.view


import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R
import com.example.fourinoneapp.data.FolderData

class HideAlbumActivity : AppCompatActivity() {

    private val FolderPaths : ArrayList<FolderData>
    get() {
        val picFolders  = ArrayList<FolderData>()
        val picPaths = ArrayList<String>()
        val allImageuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID)
        val cursor = this.contentResolver.query(allImageuri, projection, null, null, null)
        try {
            cursor?,moveToFirst()
            do{
                val folds = ImageFolder()
                val name = cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val folderpaths = datapath.substring(0, datapath.lastIndexOf("$folder/"))
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hide_gallery);
    }
}

/*
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R
import android.provider.MediaStore
import android.widget.TextView
import android.os.Environment.MEDIA_MOUNTED
import android.os.Environment
import java.util.*
import kotlin.collections.ArrayList
*/

/*
class HideAlbumActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_hide_gallery)

        imageView = findViewById<View>(R.id.folderimage) as ImageView?

        imageView!!.setOnClickListener(object : View.OnClickListener {
            override fun  onClick(v: View) {
                val intent = Intent()
                //Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type="image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, REQUEST_CODE)
            }
        })

        /*
        textView = findViewById<View>(R.id.foldername) as TextView?

        textView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val folders = file.list()
                val folderList = new ArrayList<>
                for(String folder : folders) {
                    Collections.addAll(folderList, folder);
                }
            }
        }

*/


    }


    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                try {
                    val 'in' = contentResolver.openInputStream(data!!.data!!)

                    val img = BitmapFactory.decodeStream('in')
                    'in'!!.close()

                    imageView!!.setImageBitmap(img)
                } catch(e: Exception) {

                }
            }
        }
    }
    */
    companion object {

        private val REQUEST_CODE = 0
    }

    /*
    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    String[] folders = file.list();
    */
}
*/