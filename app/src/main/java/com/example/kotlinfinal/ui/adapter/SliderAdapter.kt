package com.gorkemarslanbogan.ecommer1234.ui.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.databinding.LayoutImageSliderBinding

class SliderAdapter(private val imageList: List<Bitmap>) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutImageSliderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindImageModel(bitmap: Bitmap) {
            binding.apply {
                imageSlide.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapter.ViewHolder {
       val viewHolder = LayoutImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: SliderAdapter.ViewHolder, position: Int) {
        holder.bindImageModel(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}