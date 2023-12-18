package com.example.kotlinfinal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.LayoutCategoriesHorizontal2Binding
import com.example.kotlinfinal.databinding.LayoutCategoriesHorizontalBinding
import com.example.kotlinfinal.helper.capitalizeAndReplace

class BaseCategoriesAdapter(
    private var itemList: MutableList<String>,
    private val onClick: (String) -> Unit,
    private val viewId: Int
) : RecyclerView.Adapter<BaseCategoriesAdapter.ViewHolder>() {

    private var selectedItemPosition = 0
    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSelectedCategories(categories: String, position: Int) {
            when (binding) {
                is LayoutCategoriesHorizontalBinding -> {
                    binding.apply {
                        categoriesName.text = categories.capitalize()
                        card.isSelected = position == selectedItemPosition
                        card.setOnClickListener {
                            onClick(categories)
                            if (selectedItemPosition != position) {
                                //eski seçili item'ı eski haline getirmek için kullanılır notifiySetDateChange
                                //gereksiz yere bütün itemları tekrar çizmesin diye performans açısından
                                //notifyItemChanged kullanılıp ilgili item'ın yeniden çizilmesini sağlıyoruz.
                                notifyItemChanged(selectedItemPosition)
                                //yeni seçili item'ı rengini değiştirmek için kullanılır
                                selectedItemPosition = position
                                notifyItemChanged(selectedItemPosition)
                            }
                        }
                    }
                }
                is LayoutCategoriesHorizontal2Binding -> {
                        binding.apply {
                            categoriesName.text = categories.capitalizeAndReplace(categories)
                            card.setOnClickListener { onClick(categories) }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewId) {
            R.layout.layout_categories_horizontal -> {
                val viewHolder = LayoutCategoriesHorizontalBinding.inflate(inflater, parent, false)
                ViewHolder(viewHolder)
            }
            R.layout.layout_categories_horizontal2 -> {
                val viewHolder = LayoutCategoriesHorizontal2Binding.inflate(inflater, parent, false)
                ViewHolder(viewHolder)
            }
            else -> throw IllegalArgumentException("Hata Bilinmeyen LayoutID")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setSelectedCategories(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
