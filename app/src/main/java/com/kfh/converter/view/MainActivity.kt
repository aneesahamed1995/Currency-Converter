package com.kfh.converter.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kfh.converter.BR
import com.kfh.converter.R
import com.kfh.converter.common.AppConstant
import com.kfh.converter.common.BundleProperty
import com.kfh.converter.databinding.ActivityMainBinding
import com.kfh.converter.view.base.ToolbarViewModel
import com.kfh.converter.view.listener.ToolbarNavigationListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val toolbarViewModel: ToolbarViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            setVariable(BR.toolbarViewModel,this@MainActivity.toolbarViewModel)
            setVariable(BR.toolbarListener,this@MainActivity.toolbarListener)
            setSupportActionBar(toolbar)
        }
        navController = findNavController(R.id.flMainContainer)
        navController.addOnDestinationChangedListener { _, _, arguments -> setToolbar(arguments) }
    }

    protected fun setToolbar(arg: Bundle?){
        arg?.let {
            it.get(BundleProperty.TOOLBAR_TITLE).let { any ->
                val toolbarTitle = when (any) {
                    is Int -> getString(any)
                    is String ->any
                    else -> AppConstant.EMPTY
                }
                val toolbarViewType = it.getInt(BundleProperty.TOOLBAR_VIEW_TYPE,-1)
                if (toolbarViewType != -1){
                    toolbarViewModel.setToolbarData(toolbarTitle,toolbarViewType)
                }
            }
        }
    }

    private val toolbarListener = object : ToolbarNavigationListener {
        override fun onClickBack(view: View) {
            onBackPressed()
        }
    }
}