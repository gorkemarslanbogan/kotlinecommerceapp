package com.example.kotlinfinal.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.kotlinfinal.R
import java.util.Locale

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.shopping)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

 fun String.capitalizeAndReplace(text: String = this, splitString: String = "-"): String {
    val words = text.split(splitString)
    val result = StringBuilder()
    for (word in words) {
        val capitalizedWord = word.trim().capitalize(Locale.ROOT)
        result.append("$capitalizedWord ")
    }
    return result.toString().trim()
}

fun Context.loadImageAsBitmap(url: String, onBitmap: (Bitmap) -> Unit) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onBitmap.invoke(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
}