package com.kfh.converter.view.base

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment : Fragment() {

    private var progressDialog: ProgressDialog? = null
    protected val navController by lazy { findNavController() }


    fun showLoader(message: Int, title: Int = 0) {
        progressDialog?.let {
            if (!it.isShowing) {
                createLoader(message, title)
            }
        } ?: run {
            createLoader(message, title)
        }
    }

    private fun createLoader(message: Int, title: Int = 0) {
        if (isAdded) {
            progressDialog = ProgressDialog(context)
            if (title != 0) {
                progressDialog?.setTitle(getString(title))
            }
            progressDialog?.setMessage(getString(message))
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }
    }

    fun hideLoader() {
        if (isAdded) {
            progressDialog?.let {
                if (it.isShowing) {
                    it.hide()
                    it.dismiss()
                }
            }
        }
    }

}