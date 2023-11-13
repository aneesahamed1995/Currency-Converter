package com.kfh.converter.view.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.kfh.converter.R
import com.kfh.converter.common.AppConstant
import com.kfh.converter.view.listener.ToolbarNavigationListener

@BindingAdapter("setToolbarVisibility")
fun setToolbarVisibility(toolbar: Toolbar, viewType: Int){
    toolbar.setVisibleGone(viewType != AppConstant.ToolbarViewTypes.HIDE_TOOLBAR)
}

@BindingAdapter(value = ["setToolbarTitle","setToolbarViewType"])
fun setTitle(textView: TextView, title:String?, viewType:Int = 0){
    when(viewType){
        AppConstant.ToolbarViewTypes.TITLE_BACK_NAV,
        AppConstant.ToolbarViewTypes.TITLE->{
            textView.setVisible()
            textView.text = title
        }
        else-> textView.setGone()
    }
}

@BindingAdapter(value = ["setToolbarViewType","navListener"],requireAll = false)
fun setBackNav(imageView: ImageView, viewType:Int = 0, navListener: ToolbarNavigationListener?=null){
    when(viewType){
        AppConstant.ToolbarViewTypes.TITLE_BACK_NAV->{
            imageView.setImageResource(R.drawable.baseline_arrow_back_24)
            imageView.setOnClickListener { navListener?.onClickBack(it) }
            imageView.setVisible()
        }
        else->{
            imageView.setGone()
            imageView.setOnClickListener(null)
        }
    }
}