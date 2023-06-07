package io.project.shorts.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.project.shorts.R

fun ImageView.showImage(url :String?){
    val option = RequestOptions().placeholder(R.drawable.peakpx).error(R.drawable.peakpx)
    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}