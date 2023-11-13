package com.kfh.converter.view.converter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kfh.converter.R
import com.kfh.converter.BR
import com.kfh.converter.common.BundleProperty
import com.kfh.converter.common.extension.getString
import com.kfh.converter.common.extension.hideKeyboard
import com.kfh.converter.common.extension.isNotNullOrEmpty
import com.kfh.converter.common.extension.observeEvent
import com.kfh.converter.common.extension.toSafeDouble
import com.kfh.converter.common.helper.ErrorHandler
import com.kfh.converter.data.network.ErrorType
import com.kfh.converter.data.network.NetworkConstant
import com.kfh.converter.databinding.FragmentConverterBinding
import com.kfh.converter.view.base.BaseFragment
import com.kfh.converter.view.exchange_rate.ExchangeRatesUiState
import com.kfh.converter.view.model.CurrencyExchangeUiState
import com.kfh.converter.view.model.UIState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConverterFragment : BaseFragment() {

    private var _binding:FragmentConverterBinding? = null
    private val binding get() = _binding!!
    private val viewModel:ConverterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel,viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        observeLiveData()
        observeBackStackEntryData()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.fromCurrencyFlow.collectLatest {
                    if (binding.edFromCurrency.getString().toSafeDouble() != it.toSafeDouble()){
                        binding.edFromCurrency.setText(it)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.toCurrencyFlow.collectLatest {
                    if (binding.edToCurrency.getString().toSafeDouble() != it.toSafeDouble()){
                        binding.edToCurrency.setText(it)
                    }
                }
            }
        }
    }

    private fun setListener(){
        binding.btnValidate.setOnClickListener {
            onValidate()
        }
        binding.tvFromCurrencySymbol.setOnClickListener {
            navController.navigate(ConverterFragmentDirections.actionToExchangeRateFragment(viewModel.exchangeRate))
        }
        binding.edIban.editText?.doOnTextChanged { _, _, _, _ -> binding.edIban.error = null }
        binding.edFromCurrency.doOnTextChanged { text, _, _, _ ->
            viewModel.updateFromCurrency(text?.toString())
        }
        binding.edToCurrency.doOnTextChanged { text, _, _, _ ->
            viewModel.updateToCurrency(text?.toString())
        }
    }

    private fun onValidate(){
        hideKeyboard(requireActivity())
        val iban = binding.edIban.editText.getString()
        if (iban.isNotEmpty()){
            viewModel.validate(iban)
        }
        else binding.edIban.error = getString(R.string.please_enter_iban_no)
    }

    private fun observeLiveData(){
        viewLifecycleOwner.observeEvent(viewModel.uiState,::handleUIState)
        viewLifecycleOwner.observeEvent(viewModel.bank){ bank->
            navController.navigate(ConverterFragmentDirections.actionToBankDetailFragment(bank))
        }
    }

    private fun observeBackStackEntryData(){
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<CurrencyExchangeUiState>(BundleProperty.SELECTED_EXCHANGE_RATE)?.observe(viewLifecycleOwner){
                viewModel.exchangeRate = it
                navController.currentBackStackEntry?.savedStateHandle?.remove<CurrencyExchangeUiState>(BundleProperty.SELECTED_EXCHANGE_RATE)
            }
    }

    private fun handleUIState(uiState: UIState<Unit>) {
        when (uiState) {
            is UIState.Loading -> {
                when(uiState.apiId){
                    NetworkConstant.ApiRequestID.VALIDATE_IBAN->{
                        if (uiState.isLoading){
                            showLoader(R.string.please_wait)
                        }
                        else hideLoader()
                    }
                }
            }
            is UIState.Failure -> {
                if (uiState.errorData.errorType == ErrorType.API_ERROR && uiState.errorData.errorMessage.isNotNullOrEmpty()){
                    showErrorAlert(uiState.errorData.errorMessage)
                }
                else ErrorHandler.handleError(requireContext(), uiState.errorData)
            }
        }
    }

    private fun showErrorAlert(message:String?){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.invalid_iban))
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(getString(android.R.string.ok)) { dialog, which -> }
        alertDialog.create().show()
    }

    // Avoid Mem-leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}