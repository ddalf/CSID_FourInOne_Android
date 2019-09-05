package com.example.fourinoneapp.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFolder
import kotlinx.android.synthetic.main.item_image_folder.view.*
import java.util.ArrayList

class ImageFolderAdapter
(private val folders: ArrayList<ImageFolder>, private val folderContx: Context, private val listenToClick: ImageClickListener) : RecyclerView.Adapter<ImageFolderAdapter.FolderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.item_image_folder, parent, false)
        return FolderHolder(cell)

    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val folder = folders[position]

        Glide.with(folderContx)
                .load(folder.firstPic)
                .apply(RequestOptions().centerCrop())
                .into(holder.folderPic)

        val drawable = folderContx.getDrawable(R.drawable.rounded_imageview)
        holder.folderPic.background = drawable
        holder.folderPic.clipToOutline = true

        val text = folder.folderName
        holder.folderName.text = text
        holder.folderPic.setOnClickListener { listenToClick.onPicClicked(folder.path!!, folder.folderName!!) }
    }

    override fun getItemCount(): Int {
        return folders.size
    }


    inner class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var folderPic: ImageView
        internal var folderName: TextView

        init {
            folderPic = itemView.folderPic
            folderName = itemView.folderName
        }
    }
}
