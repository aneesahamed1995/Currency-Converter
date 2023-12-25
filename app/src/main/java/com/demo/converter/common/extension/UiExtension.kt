package com.demo.converter.common.extension

import android.content.Context
import android.widget.Toast

fun Context?.showToast(messageId: Int, length: Int = Toast.LENGTH_SHORT): Toast? {
    return this?.let {
        Toast.makeText(it, it.getString(messageId), length).also { toast->
            toast.show()
        }
    }
}

fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT):Toast? {
    if (message.isNotNullOrEmpty()){
        return Toast.makeText(this, message, length).also {
            it.show()
        }
    }
    return null
}