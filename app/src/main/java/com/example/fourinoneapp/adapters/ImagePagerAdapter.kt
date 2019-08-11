package com.example.fourinoneapp.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.IndicatorHolder
import com.example.fourinoneapp.listeners.ImageIndicatorListener
import com.example.fourinoneapp.models.ImageFacer
import java.util.ArrayList

/**
 * Author CodeBoy722
 */
class ImagePagerAdapter
/**
 *
 * @param pictureList ArrayList of ImageFacer objects
 * @param pictureContx The Activity of fragment context
 * @param imageListerner Interface for communication between adapter and fragment
 */
(internal var pictureList: ArrayList<ImageFacer>, internal var pictureContx: Context, private val imageListerner: ImageIndicatorListener) : RecyclerView.Adapter<IndicatorHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.indicator_holder, parent, false)
        return IndicatorHolder(cell)
    }

    override fun onBindViewHolder(holder: IndicatorHolder, position: Int) {

        val pic = pictureList[position]

        holder.positionController.setBackgroundColor(if (pic.selected!!) Color.parseColor("#00000000") else Color.parseColor("#8c000000"))

        Glide.with(pictureContx)
                .load(pic.picturePath)
                .apply(RequestOptions().centerCrop())
                .into(holder.image)

        holder.image.setOnClickListener {
            //holder.card.setCardElevation(5);
            pic.selected = true
            notifyDataSetChanged()
            imageListerner.onImageIndicatorClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return pictureList.size
    }
}
