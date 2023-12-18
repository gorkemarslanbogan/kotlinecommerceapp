package com.gorkemarslanbogan.ecommer1234.ui.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinal.databinding.LayoutEditProfileInputBinding
import com.google.android.material.textfield.TextInputLayout
import com.gorkemarslanbogan.ecommer1234.model.EditProfileModel

class EditProfileAdapter(private var itemList: List<EditProfileModel>) : RecyclerView.Adapter<EditProfileAdapter.ViewHolder>() {

    private var isEnabled = false
    private val userDataList = itemList
    inner class ViewHolder(val binding: LayoutEditProfileInputBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SuspiciousIndentation")
        fun bindEdittextItem(editProfileModel: EditProfileModel, isEnabled: Boolean) {
            binding.apply {
                textField.hint = editProfileModel.optionsName
                propertyName.text = editProfileModel.optionsName
                textField.editText?.setText(editProfileModel.optionsNameValue)
                textField.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                textField.isEnabled = isEnabled

                // TODO: GORKEM ARSLANBOGAN 12.12.2023
                //her edittexti dinleyip kullanıcının yazdığı değerleri dinleyip modele kaydetmek için kullanılıyor..
                if (isEnabled) {
                    binding.textField.editText?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            itemList[adapterPosition].optionsNameValue = s.toString()
                        }
                    })

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProfileAdapter.ViewHolder {
        val viewHolder = LayoutEditProfileInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: EditProfileAdapter.ViewHolder, position: Int) {
        holder.bindEdittextItem(itemList[position], isEnabled)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    //kullanıcı verileri kaydetmek istediğinde ilgili verileri adapterdan almak için yapıldı sadece güncellenmiş verileri çekip getirir.
    fun getUpdatedModels(): List<EditProfileModel> {
        return itemList.filter { itemList ->
            userDataList.any { updatedModel -> updatedModel.optionsNameValue != itemList.optionsNameValue }
        }
    }

    //kullanıcı profili düzenle dediğinde edittextlerin yazı yazabilme özelliğini aktif etmek için kullanıldı.
    @SuppressLint("NotifyDataSetChanged")
    fun openEdittextWritable(isEnabled: Boolean) {
        this.isEnabled = isEnabled
        notifyDataSetChanged()
    }
}