package com.example.fourinoneapp.listeners

import com.example.fourinoneapp.adapters.viewholders.PicHolder
import com.example.fourinoneapp.viewmodels.pictureFacer
import java.util.ArrayList

interface itemClickListener {
    fun onPicClicked(holder: PicHolder, position: Int, pics: ArrayList<pictureFacer>)
    fun onPicClicked(pictureFolderPath: String, folderName: String)
}
