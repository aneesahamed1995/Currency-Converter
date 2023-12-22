package com.demo.converter.view.currency_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.demo.converter.R
import com.demo.converter.common.extension.getOrDefault
import com.demo.converter.view.base.BaseFragment
import com.demo.converter.view.model.CurrencyItemUiState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExchangeRatesFragment:BaseFragment() {

    private val arg:ExchangeRatesFragmentArgs by navArgs()
    private val viewModel:CurrencyListViewModel by viewModel{ parametersOf(arg.exchangeRate?.code.getOrDefault<String>()) }


}

@Composable
fun CurrencyListScreen(viewModel: CurrencyListViewModel, onClickCurrencyItem:(item:CurrencyItemUiState)->Unit){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    CurrencyListScreen(uiState,onClickCurrencyItem)
    LaunchedEffect(Unit){
        viewModel.getCurrencies()
    }
}

@Composable
fun CurrencyListScreen(uiState:CurrencyListUiState, onClickCurrencyItem:(item:CurrencyItemUiState)->Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
    ){
        when{
            uiState.currencyExchanges.isNotEmpty()-> CurrencyListScreen(uiState.currencyExchanges,onClickCurrencyItem)
            uiState.isLoading-> ProgressBar()
            else-> EmptyStateMessage(message = stringResource(id = R.string.no_currencies_available))
        }
    }
}

@Composable
fun CurrencyListScreen(currencyUiItems:List<CurrencyItemUiState>, onClickCurrencyItem:(item:CurrencyItemUiState)->Unit){
    LazyColumn(contentPadding = PaddingValues(all = 16.dp),verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(items  = currencyUiItems){ item->
            CurrencyUiItem(item,onClickCurrencyItem)
        }
    }
}

@Composable
fun CurrencyUiItem(currencyUiItem: CurrencyItemUiState, onClickCurrencyItem:(item:CurrencyItemUiState)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickCurrencyItem(currencyUiItem) },
        elevation = 2.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.surface
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.currency_symbol_placeholder, formatArgs = arrayOf(currencyUiItem.name, currencyUiItem.code)), fontWeight = FontWeight.Bold, modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp))
            RadioButton(modifier = Modifier.size(24.dp), selected = currencyUiItem.isSelected, onClick = { onClickCurrencyItem(currencyUiItem)})
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
private fun CurrencyItemUiPreview(){
    MaterialTheme {
        CurrencyUiItem(CurrencyItemUiState(
            "Kuwait Dinar Dinar Dinar DinarDinarDinar","KWD",true
        )){}
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CurrencyUiItemsPreview(){
    MaterialTheme {
        val items = mutableListOf<CurrencyItemUiState>().also { list->
            repeat(10){
                list.add(CurrencyItemUiState(
                    "Kuwait Dinar Dinar Dinar DinarDinarDinar","KWD",true
                ))
            }
        }
        CurrencyListScreen(items){}
    }
}