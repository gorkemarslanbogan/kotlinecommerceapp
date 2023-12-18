package com.example.kotlinfinal.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.databinding.FragmentProductDetailsBinding
import com.example.kotlinfinal.helper.Utils.hideNavigationBarAndAppbar
import com.example.kotlinfinal.helper.Utils.showShortToast
import com.example.kotlinfinal.helper.loadImageAsBitmap
import com.example.kotlinfinal.ui.MainActivity
import com.example.kotlinfinal.ui.viewmodel.ProductsDetailsFragmentViewModel
import com.gorkemarslanbogan.ecommer1234.ui.adapter.SliderAdapter


class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    lateinit var viewModel: ProductsDetailsFragmentViewModel
    private lateinit var product: Product
    private lateinit var productPhotoAdapter: SliderAdapter
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        hideNavigationBarAndAppbar(this, true, true)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[ProductsDetailsFragmentViewModel::class.java]

        val receivedBundle = arguments
        if (receivedBundle != null) {
            val value = receivedBundle.getString("productId")
            value.let {
                viewModel.getProductById(value!!)
            }
        }
        viewModel.singleProduct.observe(viewLifecycleOwner) {
            product = it
            setProductDataInUIComponent(it)
        }

        viewModel.productIsLike.observe(viewLifecycleOwner) {
            setLikeButtonVisibility(it)
        }

        viewModel.productDisLike.observe(viewLifecycleOwner) {
            setLikeButtonVisibility(it)
        }

        binding.addToCard.setOnClickListener {
            viewModel.singleProduct.value?.let { product ->
            viewModel.addToCard(product)
            showShortToast(requireContext(), requireContext().getString(R.string.str_sepete_eklendi))
            }
        }

        binding.addFavorite.setOnClickListener {
            viewModel.addFavorite(product)
        }

        binding.removeFavorite.setOnClickListener {
            viewModel.removeFavorite(product)
        }


        //sepetteki icona yansıtması için bu yöntemi bulabildim hocam..
        viewModel.size.observe(viewLifecycleOwner) {
            val mainActivity = requireActivity()
            if(mainActivity is MainActivity){
                mainActivity.setBadge(it)
            }
        }

        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun setProductDataInUIComponent(product: Product){
        binding.productNameTextview.text = product.title
        binding.productDescTextview.text = product.description
        binding.productPrice.text =  "$ ${product.price}"
        binding.productStock.text = product.stock.toString()
        binding.productStar.text = product.rating.toString()
        binding.productCategory.text = product.category
        //ürün fotoğraf galerisi için internetten
        // gelen veriyi bitmape çevirip recylerview adapter'a yükledim.
        if(!product.images.isNullOrEmpty()) {
            val bitmapList = mutableListOf<Bitmap>()
            product.images.forEach { it ->
                requireContext().loadImageAsBitmap(it, onBitmap = {
                    //asenkron işlem olduğundan ötürü gereksiz yere sürekli adapter'ı set etmek
                    //zorunda kalıyorum burada bundan başka yapabileceğim birşey göremedim
                    //sunucu cevap hızı ve resim boyutları değişken olduğundan ötürü adapter
                    //set etmeyi burada yaptım..
                    bitmapList.add(it)
                    productPhotoAdapter = SliderAdapter(bitmapList)
                    binding.productImage.adapter = productPhotoAdapter
                })
            }
        }
        //yorum ve beğeni oranları servisten gelmediği için her zaman aynı kalmasın diye random sayılar üretiliyor..
        val reviews = viewModel.randomInRange(100,300)
        val like = viewModel.randomInRange(50,100)
        binding.customerLikeText.text = "%$like"
        binding.productReviews.text = "$reviews Reviews"
    }

    fun setLikeButtonVisibility(isLike: Boolean) {
        if(isLike){
            binding.addFavorite.visibility = View.GONE
            binding.removeFavorite.visibility = View.VISIBLE
        }else {
            binding.addFavorite.visibility = View.VISIBLE
            binding.removeFavorite.visibility = View.GONE
        }
    }


    //fragment kapanırken navigation barın visible yapmak için kullanıyorum burayı
    override fun onDestroyView() {
        super.onDestroyView()
        hideNavigationBarAndAppbar(this,false)
    }
}