package com.demo.converter.common.extension

import android.widget.EditText
import com.demo.converter.common.AppConstant

fun EditText?.getString():String{
    if(this!=null){
        return this.text.toString().trim()
    }
    return AppConstant.EMPTY
}