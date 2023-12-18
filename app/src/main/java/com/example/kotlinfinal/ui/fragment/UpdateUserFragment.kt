package com.gorkemarslanbogan.ecommer1234.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.databinding.FragmentUpdateUserBinding
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.helper.Utils.showShortToast
import com.example.kotlinfinal.helper.loadImage
import com.example.kotlinfinal.ui.viewmodel.UpdateUserFragmentViewModel
import com.gorkemarslanbogan.ecommer1234.model.EditProfileModel
import com.gorkemarslanbogan.ecommer1234.ui.adapter.EditProfileAdapter
import java.util.Locale


class UpdateUserFragment : Fragment() {
    private lateinit var binding: FragmentUpdateUserBinding
    private lateinit var editProfileAdapter: EditProfileAdapter
    private lateinit var viewModel: UpdateUserFragmentViewModel
    private val jwtUser: JwtUser by lazy { SharedManagment.getJwtModel() }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentUpdateUserBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[UpdateUserFragmentViewModel::class.java]

        viewModel.setEdittextProfileList(jwtUser)

        viewModel.isSuccesful.observe(viewLifecycleOwner){
            if (it){
                showShortToast(requireContext(), getString(R.string.str_islem_basarili))
            }else {
                showShortToast(requireContext(), getString(R.string.str_hata))
            }
            setVisibitlyUIButton(false)
        }

        viewModel.edittextProfileModel.observe(viewLifecycleOwner){
            editProfileAdapter = EditProfileAdapter(it)
            binding.rcAdapter.adapter = editProfileAdapter
        }

        binding.saveChangesProfileButton.setOnClickListener {
            viewModel.setUpdateModel(editProfileAdapter.getUpdatedModels(), jwtUser)
        }

        binding.editProfileTextButton.setOnClickListener {
            setVisibitlyUIButton()
        }

        binding.nameSurname.text = "${jwtUser.firstName?.capitalize(Locale.ROOT)} ${jwtUser.lastName?.capitalize(Locale.ROOT)}"

        binding.editProfileIptalTextButton.setOnClickListener {
            setVisibitlyUIButton(false)
            //iptal edilirse varsıyalan dataları geri yükler.
            viewModel.setEdittextProfileList(jwtUser)
        }

        return binding.root
    }

    private fun setVisibitlyUIButton(isEnable: Boolean = true) {
        if(isEnable) {
            binding.editProfileIptalTextButton.visibility = View.VISIBLE
            binding.saveChangesProfileButton.visibility = View.VISIBLE
            binding.editProfileTextButton.visibility = View.GONE
            binding.editProfileIptalTextButton.gravity = Gravity.END
        }else {
            binding.editProfileIptalTextButton.visibility = View.GONE
            binding.saveChangesProfileButton.visibility = View.GONE
            binding.editProfileTextButton.visibility = View.VISIBLE
            binding.editProfileIptalTextButton.gravity = Gravity.START
        }
        editProfileAdapter.openEdittextWritable(isEnable)
    }
}