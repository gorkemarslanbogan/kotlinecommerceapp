package com.example.kotlinfinal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.data.entity.CardProduct
import com.example.kotlinfinal.databinding.OrderCardHorizontalBinding
import com.example.kotlinfinal.helper.loadImage

class OrderItemAdapter(private var itemList: List<CardProduct>) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: OrderCardHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
         fun bindCardProduct(product: CardProduct) {
            binding.apply {
                    productNameTextview.text = product.title
                    val totalPrice = product.price * product.quantity
                    productTotalPrice.text = "| $totalPrice"
                    if(product.thumbnail.isNotEmpty()){
                        productImage.loadImage(product.thumbnail)
                    }
                    productStock.text = product.quantity.toString()
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemAdapter.ViewHolder {
        val viewHolder = OrderCardHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: OrderItemAdapter.ViewHolder, position: Int) {
        holder.bindCardProduct(itemList[position])

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}