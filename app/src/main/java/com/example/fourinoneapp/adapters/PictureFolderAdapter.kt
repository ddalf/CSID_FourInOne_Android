package com.example.fourinoneapp.adapters

import android.content.Context
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.listeners.itemClickListener
import com.example.fourinoneapp.viewmodels.imageFolder
import kotlinx.android.synthetic.main.picture_folder_item.view.*
import java.util.ArrayList

class PictureFolderAdapter
(private val folders: ArrayList<imageFolder>, private val folderContx: Context, private val listenToClick: itemClickListener) : RecyclerView.Adapter<PictureFolderAdapter.FolderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.picture_folder_item, parent, false)
        return FolderHolder(cell)

    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val folder = folders[position]

        Glide.with(folderContx)
                .load(folder.firstPic)
                .apply(RequestOptions().centerCrop())
                .into(holder.folderPic)

        val text = "(" + folder.numberOfPics + ") " + folder.folderName
        holder.folderName.text = text
        holder.folderPic.setOnClickListener { listenToClick.onPicClicked(folder.path!!, folder.folderName!!) }
    }

    override fun getItemCount(): Int {
        return folders.size
    }


    inner class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var folderPic: ImageView
        internal var folderName: TextView
        internal var folderCard: CardView

        init {
            folderPic = itemView.folderPic
            folderName = itemView.folderName
            folderCard = itemView.folderCard
        }
    }
}
