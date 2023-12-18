package com.example.kotlinfinal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.databinding.ProductCardHorizontalBinding
import com.example.kotlinfinal.helper.Utils.showShortToast
import com.example.kotlinfinal.helper.loadImage

class CardsAdapter(
    private var itemList: List<Product>,
    private val onDeleteButton: (Product) -> Unit,
    private val onstokProcess: (List<Product>) -> Unit) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ProductCardHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
         fun bindCardProduct(product: Product) {
            binding.apply {
                    productNameTextview.text = product.title
                    productPrice.text = "$ ${product.price}"
                    if(!product.images.isNullOrEmpty()){
                        productImage.loadImage(product.images[0])
                    }
                    binding.deleteButton.setOnClickListener {onDeleteButton(product)}
                    stockCount.text = product.stock.toString()
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsAdapter.ViewHolder {
        val viewHolder = ProductCardHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CardsAdapter.ViewHolder, position: Int) {
        holder.bindCardProduct(itemList[position])
        holder.binding.stockPlusButton.setOnClickListener { increaseStock(holder) }
        holder.binding.stockNegativeButton.setOnClickListener { reduceStock(holder) }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun increaseStock(holder: ViewHolder)  {
        return try {
            var stockAsLong = holder.binding.stockCount.text.toString().toLong()
            if(itemList[holder.adapterPosition].stock >= stockAsLong){
                stockAsLong++
                itemList[holder.adapterPosition].stock = stockAsLong
                holder.binding.stockCount.text = stockAsLong.toString()
                onstokProcess(itemList)
            }else {
                showShortToast(holder.itemView.context,
                    holder.itemView.context.getString(R.string.str_yetersiz_stok))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun reduceStock(holder: ViewHolder) {
         try {
            var stockAsLong = holder.binding.stockCount.text.toString().toLong()
            if(stockAsLong > 1){
                stockAsLong--
                itemList[holder.adapterPosition].stock = stockAsLong
                holder.binding.stockCount.text = stockAsLong.toString()
                onstokProcess(itemList)
            }else {
                showShortToast(holder.itemView.context,
                    holder.itemView.context.getString(R.string.str_stok_sifir_olamaz))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getCardProductList() : List<Product> {
        return itemList
    }


}