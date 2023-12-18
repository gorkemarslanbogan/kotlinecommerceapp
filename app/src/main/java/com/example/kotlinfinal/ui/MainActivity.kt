package com.example.kotlinfinal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.databinding.LayoutActivityMainBinding
import com.example.kotlinfinal.helper.CardManager
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.helper.capitalizeAndReplace
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var binding: LayoutActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val cardManager: CardManager by lazy { CardManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(binding.mainActivity.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        SharedManagment.getSharedInstance(applicationContext)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })


        binding.mainActivity.bottomNavigationBar.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)

        binding.mainActivity.appBar.appBarHomeIcon.setOnClickListener {
            drawerOpenCloseSet()
        }

        // appbarda sepet butonuna tıklayınca sepet fragmentına gitmek için yapıldı..

        binding.mainActivity.appBar.cardButton.setOnClickListener {
            navController.navigate(R.id.cardsFragment)
        }

        // sepetin iconu üstünde kaç adet ürün olduğunu gösteren view'ı set ettik
        getCardItemCountAndSetBadge()


        //Eğer stack varsa home menü iconu kapatıp geri tuşunu tetikler
        //HomePageFragment hariç orası anasayfa çünkü
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homePageFragment) {
                binding.mainActivity.appBar.appBarHomeIcon.visibility = View.VISIBLE
                binding.mainActivity.appBar.backButton.visibility = View.GONE
            } else {
                binding.mainActivity.appBar.appBarHomeIcon.visibility = View.GONE
                binding.mainActivity.appBar.backButton.visibility = View.VISIBLE
            }
        }

        // Geri tuşu için geri gitme özelliğini ekleme
        binding.mainActivity.appBar.backButton.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun getCardItemCountAndSetBadge() {
        val itemSize = cardManager.getProducts().size
        setBadge(itemSize)
    }

    fun setBadge(itemSize: Int){
        if(itemSize > 0) {
            binding.mainActivity.appBar.badgeTextView.visibility = View.VISIBLE
            binding.mainActivity.appBar.badgeTextView.text = itemSize.toString()
        } else {
            binding.mainActivity.appBar.badgeTextView.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    fun setNavHeader(jwtUser: JwtUser){
            val navHeaderView = binding.navView.getHeaderView(0)
            val textView = navHeaderView.findViewById<TextView>(R.id.welcomeTextview)
            textView.text = "Welcome ${jwtUser.firstName?.capitalizeAndReplace()}"

    }

    private fun drawerOpenCloseSet(){
        val isDrawerOpen = binding.drawerLayout
        if(isDrawerOpen.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}