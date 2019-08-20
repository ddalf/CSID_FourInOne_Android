package com.example.fourinoneapp.listeners

import com.example.fourinoneapp.adapters.viewholders.ImageHolder
import com.example.fourinoneapp.models.ImageFacer
import java.util.ArrayList

interface ImageClickListener {
    fun onPicClicked(holder: ImageHolder, position: Int, pics: ArrayList<ImageFacer>)
    fun onPicClicked(pictureFolderPath: String, folderName: String)
}
