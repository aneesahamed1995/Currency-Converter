package com.kfh.converter.common.extension

import android.widget.EditText
import com.kfh.converter.common.AppConstant

fun EditText?.getString():String{
    if(this!=null){
        return this.text.toString().trim()
    }
    return AppConstant.EMPTY
}