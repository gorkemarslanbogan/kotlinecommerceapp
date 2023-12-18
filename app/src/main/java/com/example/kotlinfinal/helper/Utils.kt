package com.example.kotlinfinal.helper

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlinfinal.R
import com.example.kotlinfinal.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

object Utils {

    fun showShortToast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun checkEdittext(context: Context, edittextArray: List<EditText>) : Boolean {
        edittextArray.forEach {
            if(it.text.isEmpty()) {
                it.error = context.getString(R.string.str_value_can_not_empty)
                return false
            }
        }
        return true
    }


     fun hideNavigationBarAndAppbar(fragment: Fragment, isVisibleAppbar: Boolean = true, isVisibleNavigation: Boolean = false) {
        //login sayfası fragment olduğuğundan activity'den gelen navigation barı gizlemem gerektiğinden
        //ötürü yazıldı //GORKEM ARSLANBOGAN
        (fragment.requireActivity() as MainActivity).
        findViewById<LinearLayout>(R.id.ll_bottomNavigationBar)
            .visibility = if(isVisibleNavigation) View.GONE else View.VISIBLE

         (fragment.requireActivity() as MainActivity).
         findViewById<LinearLayout>(R.id.appBar)
             .visibility = if(isVisibleAppbar) View.GONE else View.VISIBLE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun hideAppbarHomeIcon(fragment: Fragment, isBackIcon: Boolean = false, title: String = "") {

        val activity = (fragment.requireActivity() as MainActivity)
        activity.findViewById<ImageView>(R.id.appBarHomeIcon).
        setImageDrawable(fragment.context?.getDrawable( if (isBackIcon) R.drawable.icon_back else R.drawable.menu_icon))

        activity.findViewById<FrameLayout>(R.id.cardButtonFrame)
            .visibility = if(isBackIcon) View.GONE else View.VISIBLE

        activity.findViewById<LinearLayout>(R.id.ll_bottomNavigationBar)
            .visibility = if(isBackIcon) View.GONE else View.VISIBLE

        activity.findViewById<TextView>(R.id.appbarTitle)
            .text = if(isBackIcon) title else fragment.context?.getText(R.string.app_name)

    }
}