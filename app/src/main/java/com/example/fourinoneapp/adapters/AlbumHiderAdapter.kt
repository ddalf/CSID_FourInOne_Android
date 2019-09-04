package com.example.fourinoneapp.adapters
import android.content.Context
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.models.ImageFolder
import kotlinx.android.synthetic.main.item_hide_album.view.*
import java.util.ArrayList

class AlbumHiderAdapter
    (private val folders: ArrayList<ImageFolder>, private val folderContx: Context) : RecyclerView.Adapter<AlbumHiderAdapter.HiderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.item_hide_album, parent, false)
        return HiderHolder(cell)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: HiderHolder, position: Int) {
        val folder = folders[position]

        Glide.with(folderContx)
            .load(folder.firstPic)
            .apply(RequestOptions().centerCrop())
            .into(holder.folderImage)

        val text = folder.folderName + "(" + folder.numberOfPics + ") "
        holder.folderName.text = text
        holder.hideSwitch.id = position
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    inner class HiderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var folderImage: ImageView
        internal var folderName: TextView
        internal var hideSwitch: Switch

        init {
            folderImage = itemView.folderImage
            folderName = itemView.folderName
            hideSwitch = itemView.hideSwitch
        }
    }
}
