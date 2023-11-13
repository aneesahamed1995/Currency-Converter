package com.kfh.converter.view.bank_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kfh.converter.BR
import com.kfh.converter.databinding.FragmentBankDetailBinding
import com.kfh.converter.view.base.BaseFragment

class BankDetailFragment:BaseFragment() {

    private val arg:BankDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentBankDetailBinding.inflate(inflater,container,false).also {
        it.lifecycleOwner = viewLifecycleOwner
        it.setVariable(BR.bankData,arg.bankData)
    }.root
}