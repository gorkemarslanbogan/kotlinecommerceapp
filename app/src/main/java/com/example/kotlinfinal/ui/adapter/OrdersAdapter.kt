package com.example.kotlinfinal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.data.entity.Cart
import com.example.kotlinfinal.databinding.OrderCardLayoutBinding

class OrdersAdapter(private var itemList: List<Cart>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: OrderCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
         fun bindOrderItem(order: Cart) {
            binding.apply {
                    orderCode.text = "Order Ref No: ${order.id}"
                    orderPrice.text = "Total: $${order.total}"
                    rcOrderItem.adapter = OrderItemAdapter(order.products)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val viewHolder = OrderCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.bindOrderItem(itemList[position])

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}