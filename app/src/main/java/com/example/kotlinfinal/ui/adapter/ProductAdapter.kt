package com.example.kotlinfinal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.databinding.ProductCardsBinding
import com.example.kotlinfinal.helper.Utils
import com.example.kotlinfinal.helper.loadImage

class ProductAdapter(private var productList: List<Product>, private val onClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ProductCardsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
         fun setProductInfoUIComponent(product: Product) {
            binding.apply {
                productNameTextview.text = product.title
                productDescTextview.text = product.description
                productPriceTextview.text = "${product.price} ${itemView.context.getString(R.string.str_tl)}"
                if (!product.images.isNullOrEmpty()) {
                    binding.productImage.loadImage(product.images[0])
                }
                card.setOnClickListener { onClick(product) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val viewHolder = ProductCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.setProductInfoUIComponent(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }


}