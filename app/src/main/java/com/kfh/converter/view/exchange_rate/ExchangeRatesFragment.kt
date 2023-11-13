package com.kfh.converter.view.exchange_rate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.kfh.converter.R
import com.kfh.converter.common.BundleProperty
import com.kfh.converter.common.extension.getOrDefault
import com.kfh.converter.domain.entity.canShowError
import com.kfh.converter.view.base.BaseFragment
import com.kfh.converter.view.model.CurrencyExchangeUiState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExchangeRatesFragment:BaseFragment() {

    private val arg:ExchangeRatesFragmentArgs by navArgs()
    private val viewModel:ExchangeRatesViewModel by viewModel{ parametersOf(arg.exchangeRate?.code.getOrDefault<String>()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                ExchangeRatesScreen(viewModel){ exchangeRate->
                    navController.previousBackStackEntry?.savedStateHandle?.set(BundleProperty.SELECTED_EXCHANGE_RATE,exchangeRate)
                    navController.popBackStack()
                }
            }
        }
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }
}

@Composable
fun ExchangeRatesScreen(viewModel: ExchangeRatesViewModel,onClickRate:(currencyExchange:CurrencyExchangeUiState)->Unit){
    val uiState = viewModel.profileUiState.collectAsStateWithLifecycle().value
    ExchangeRatesScreen(uiState,onClickRate)
    LaunchedEffect(Unit){
        viewModel.getExchangeRates()
    }
}

@Composable
fun ExchangeRatesScreen(uiState:ExchangeRatesUiState,onClickRate:(currencyExchange:CurrencyExchangeUiState)->Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
    ){
        when{
            uiState.currencyExchanges.isNotEmpty()-> ExchangeRatesList(uiState.currencyExchanges,onClickRate)
            uiState.isLoading-> ProgressBar()
            uiState.error != null && uiState.error.canShowError-> EmptyStateMessage(message = uiState.error.errorMessage.getOrDefault())
            uiState.currencyExchanges.isNotEmpty()-> EmptyStateMessage(message = stringResource(id = R.string.no_exchange_rates_available))
        }
    }
}

@Composable
fun ExchangeRatesList(exchanges:List<CurrencyExchangeUiState>, onClick:(currencyExchange:CurrencyExchangeUiState)->Unit){
    LazyColumn(contentPadding = PaddingValues(all = 16.dp),verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(items  = exchanges, key = {exchange-> exchange.code}){ exchange->
            ExchangeRateItem(exchange = exchange, onClick = onClick)
        }
    }
}

@Composable
fun ExchangeRateItem(exchange: CurrencyExchangeUiState, onClick:(currencyExchange:CurrencyExchangeUiState)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(exchange) },
        elevation = 2.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.surface
    ){
        ConstraintLayout(Modifier.fillMaxWidth().padding(16.dp)) {
            val (tvTitle,radioButton,tvExchangeRate) = createRefs()
            Text(text = stringResource(id = R.string.currency_symbol_placeholder, formatArgs = arrayOf(exchange.name, exchange.code)), fontWeight = FontWeight.Bold, modifier = Modifier.constrainAs(tvTitle){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(radioButton.start)
                width = Dimension.fillToConstraints
            })
            RadioButton(modifier = Modifier.size(24.dp).constrainAs(radioButton){
                 end.linkTo(parent.end)
                top.linkTo(tvTitle.top)
            }, selected = exchange.isSelected, onClick = { onClick(exchange)})
            Text(text = stringResource(id = R.string.exchange_rate_placeholder, exchange.exchangeRate.toString()), modifier = Modifier.padding(top = 8.dp).constrainAs(tvExchangeRate){
                top.linkTo(tvTitle.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        }
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyStateMessage(message:String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = message, color = MaterialTheme.colors.onSurface)
    }
}

@Preview
@Composable
private fun ExchangeRateItemPreview(){
    MaterialTheme {
        ExchangeRateItem(CurrencyExchangeUiState(
            "Kuwait Dinar Dinar Dinar DinarDinarDinar","KWD",123.123
        )){}
    }
}