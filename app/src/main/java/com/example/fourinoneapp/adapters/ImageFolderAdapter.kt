package com.example.fourinoneapp.adapters

import android.content.Context
<<<<<<< HEAD
=======
<<<<<<< HEAD
import android.util.Log
import androidx.cardview.widget.CardView
=======
>>>>>>> 85f513a41c6d44ba68aaee4f9362e4a64a5efec2
import androidx.recyclerview.widget.RecyclerView
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.folderFac
import kotlinx.android.synthetic.main.item_image_folder.view.*



class ImageFolderAdapter
<<<<<<< HEAD
    (private val folderContx: Context, private val listenToClick: ImageClickListener,private var folders : List<folderFac>) : RecyclerView.Adapter<ImageFolderAdapter.FolderHolder>() {
=======
(private val folders: ArrayList<ImageFolder>, private val folderContx: Context, private val listenToClick: ImageClickListener,private var hides : Set<String>) : RecyclerView.Adapter<ImageFolderAdapter.FolderHolder>() {
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.item_image_folder, parent, false)
        return FolderHolder(cell)

    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val folder = folders[position]

<<<<<<< HEAD
            Glide.with(folderContx)
                .load(folder.firstPic)
                .apply(RequestOptions().centerCrop())
                .into(holder.folderPic)


            val drawable = folderContx.getDrawable(R.drawable.rounded_imageview)
            holder.folderPic.background = drawable
            holder.folderPic.clipToOutline = true

            val text = folder.folderName
            holder.folderName.text = text
            holder.folderPic.setOnClickListener {
                listenToClick.onPicClicked(
                    folder.path!!,
                    folder.folderName!!
                )
            }

=======
            if(hides.elementAt(position).subSequence(0,4).equals("true")) {
                Glide.with(folderContx)
                    .load(folder.firstPic)
                    .apply(RequestOptions().centerCrop())
                    .into(holder.folderPic)

<<<<<<< HEAD
                val text = folder.folderName
                holder.folderName.text = text
                holder.folderPic.setOnClickListener {
                    listenToClick.onPicClicked(
                        folder.path!!,
                        folder.folderName!!
                    )
                }
            }
=======
        val drawable = folderContx.getDrawable(R.drawable.rounded_imageview)
        holder.folderPic.background = drawable
        holder.folderPic.clipToOutline = true

        val text = folder.folderName
        holder.folderName.text = text
        holder.folderPic.setOnClickListener { listenToClick.onPicClicked(folder.path!!, folder.folderName!!) }
>>>>>>> 85f513a41c6d44ba68aaee4f9362e4a64a5efec2
>>>>>>> f1c544d7c31b4d45bff8846c06060ddf2f027ad8
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