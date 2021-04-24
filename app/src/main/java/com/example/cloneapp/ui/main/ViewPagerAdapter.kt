package com.example.cloneapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.cloneapp.R

class ViewPagerAdapter(val context: Context, private val items: ArrayList<String>) :
        RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(items[position])
            .apply(RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            )
            .error(R.drawable.noimage)
            .into(holder.imageView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_slide_image)
    }

}