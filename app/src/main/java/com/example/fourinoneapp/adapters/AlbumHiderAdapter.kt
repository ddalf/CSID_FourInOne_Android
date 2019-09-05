package com.example.fourinoneapp.adapters
import android.content.Context
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.models.ImageFolder
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.item_hide_album.view.*
import java.util.ArrayList
import java.util.HashSet

class AlbumHiderAdapter
    (private val folders: ArrayList<ImageFolder>, private val folderContx: Context, private var hides:Set<Int>) : RecyclerView.Adapter<AlbumHiderAdapter.HiderHolder>() {

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

        val dPreferences = folderContx.getSharedPreferences("dFile", Context.MODE_PRIVATE)
        val hideList = dPreferences.getStringSet("hides", null)
        val exTe = HashSet<String>()

        Glide.with(folderContx)
            .load(folder.firstPic)
            .apply(RequestOptions().centerCrop())
            .into(holder.folderImage)

        val text = folder.folderName + "(" + folder.numberOfPics + ") "
        holder.folderName.text = text
        holder.hideSwitch.id = position
        if (hideList != null) {
            if (hideList.elementAt(position).subSequence(0, 4).equals("true")) {
                holder.hideSwitch.setChecked(true)
            } else {
                holder.hideSwitch.setChecked(false)
            }

//            holder.hideSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//                val editor = dPreferences.edit()
//                editor.clear()
//
//                if (isChecked) {
//                    for (i in 0..hideList.size) {
//                        for(i2 in hides){
//                            if(i2.toInt() == i){
//                                exTe.add("true" + i)
//                                break;
//                            }
//                            if(i2.toInt() == hides.elementAt(hides.size-1) && i2.toInt() != i){
//                                exTe.add("true" + i)
//                            }
//                        }
//                    }
//                } else {
//                    for (i in 0..hides.size) {
//                        if (i == position) {
//                            exTe.add("false" + i)
//                        }
//                        exTe.add("true" + i)
//                    }
//                }
//                editor.putStringSet("hides", exTe)
//                editor.commit()
//            }
        }
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
