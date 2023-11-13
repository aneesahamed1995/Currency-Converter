package com.kfh.converter.view.binding

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.flow.MutableStateFlow


fun View?.setVisible(){
    this?.visibility = View.VISIBLE
}

fun View?.setInVisible(){
    this?.visibility = View.INVISIBLE
}

fun View?.setVisibleGone(show: Boolean){
    this?.visibility = if (show) View.VISIBLE else View.GONE
}

fun View?.setGone(){
    this?.visibility = View.GONE
}