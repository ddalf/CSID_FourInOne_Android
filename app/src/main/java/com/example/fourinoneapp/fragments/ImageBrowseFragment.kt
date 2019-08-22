package com.example.fourinoneapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

import androidx.core.view.ViewCompat.setTransitionName
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.ImagePagerAdapter
import com.example.fourinoneapp.listeners.ImageIndicatorListener
import com.example.fourinoneapp.models.ImageFacer
import kotlinx.android.synthetic.main.picture_browser_pager.view.*

class ImageBrowseFragment : Fragment, ImageIndicatorListener {

    private var allImages = ArrayList<ImageFacer>()
    private var position: Int = 0
    private lateinit var animeContx: Context
    private var image: ImageView? = null
    private var imagePager: ViewPager? = null
    private var indicatorRecycler: RecyclerView? = null
    private var viewVisibilityController: Int = 0
    private var viewVisibilitylooper: Int = 0
    private var pagingImages: ImagesPagerAdapter? = null
    private var previousSelected = -1

    constructor() {

    }

    constructor(allImages: ArrayList<ImageFacer>, imagePosition: Int, anim: Context) {
        this.allImages = allImages
        this.position = imagePosition
        this.animeContx = anim
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.picture_browser, container, false)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewVisibilityController = 0
        viewVisibilitylooper = 0

        imagePager = view.findViewById(R.id.imagePager)
        pagingImages = ImagesPagerAdapter()
        imagePager!!.adapter = pagingImages
        imagePager!!.offscreenPageLimit = 3
        imagePager!!.currentItem = position//displaying the image at the current position passed by the ImageDisplay Activity

        indicatorRecycler = view.findViewById(R.id.indicatorRecycler)
        indicatorRecycler!!.hasFixedSize()
        indicatorRecycler!!.layoutManager = GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
        val indicatorAdapter = ImagePagerAdapter(allImages, this.context!!, this)
        indicatorRecycler!!.adapter = indicatorAdapter

        allImages[position].selected = true
        previousSelected = position
        indicatorAdapter.notifyDataSetChanged()
        indicatorRecycler!!.scrollToPosition(position)


        imagePager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                if (previousSelected != -1) {
                    allImages[previousSelected].selected = false
                    previousSelected = position
                    allImages[position].selected = true
                    indicatorRecycler!!.adapter!!.notifyDataSetChanged()
                    indicatorRecycler!!.scrollToPosition(position)
                } else {
                    previousSelected = position
                    allImages[position].selected = true
                    indicatorRecycler!!.adapter!!.notifyDataSetChanged()
                    indicatorRecycler!!.scrollToPosition(position)
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        indicatorRecycler!!.setOnTouchListener { v, event ->
            false
        }

    }

    override fun onImageIndicatorClicked(ImagePosition: Int) {
        if (previousSelected != -1) {
            allImages[previousSelected].selected = false
            previousSelected = ImagePosition
            indicatorRecycler!!.adapter!!.notifyDataSetChanged()
        } else {
            previousSelected = ImagePosition
        }

        imagePager!!.currentItem = ImagePosition
    }

    private inner class ImagesPagerAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return allImages.size
        }

        override fun instantiateItem(containerCollection: ViewGroup, position: Int): Any {
            val layoutinflater = containerCollection.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutinflater.inflate(R.layout.picture_browser_pager, null)
            image = view.image

            setTransitionName(image!!, position.toString() + "picture")

            val pic = allImages[position]
            Glide.with(animeContx)
                    .load(pic.picturePath)
                    .apply(RequestOptions().fitCenter())
                    .into(image!!)

            image!!.setOnClickListener {
                if (indicatorRecycler!!.visibility == View.GONE) {
                    indicatorRecycler!!.visibility = View.VISIBLE
                } else {
                    indicatorRecycler!!.visibility = View.GONE
                }
            }
            (containerCollection as ViewPager).addView(view)
            return view
        }

        override fun destroyItem(containerCollection: ViewGroup, position: Int, view: Any) {
            (containerCollection as ViewPager).removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

    private fun visibiling() {
        viewVisibilityController = 1
        val checker = viewVisibilitylooper
        Handler().postDelayed({
            if (viewVisibilitylooper > checker) {
                visibiling()
            } else {
                indicatorRecycler!!.visibility = View.GONE
                viewVisibilityController = 0

                viewVisibilitylooper = 0
            }
        }, 4000)
    }

    companion object {

        fun newInstance(allImages: ArrayList<ImageFacer>, imagePosition: Int, anim: Context): ImageBrowseFragment {
            return ImageBrowseFragment(allImages, imagePosition, anim)
        }
    }

}
