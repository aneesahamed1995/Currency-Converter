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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.demo.converter.R
import com.demo.converter.common.extension.isNotNullOrEmpty
import com.demo.converter.common.extension.showToast
import com.demo.converter.view.model.CurrencyItemUiState

@Composable
fun CurrencyListScreen(viewModel: CurrencyListViewModel, onSelectedCurrencyItem:(currencyCode:String)->Unit){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    CurrencyListScreen(uiState){
        viewModel.getExchangeRates(it.code)
    }
    LaunchedEffect(Unit){
        viewModel.getCurrencies()
    }
    LaunchedEffect(uiState){
        if (uiState.exchangeRatesSynced){
            onSelectedCurrencyItem(uiState.selectedCurrencyCode)
        }
    }
    if (uiState.errorMessage.isNotNullOrEmpty()){
        LocalContext.current.showToast(uiState.errorMessage)
        viewModel.errorMessageShown()
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

        if (uiState.showProgressDialog){
            AppProgressDialog(title = stringResource(R.string.please_wait))
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
            Text(text = stringResource(id = R.string.currency_symbol_placeholder, formatArgs = arrayOf(currencyUiItem.name, currencyUiItem.code)), color = MaterialTheme.colors.onSecondary, fontWeight = if (currencyUiItem.isSelected) FontWeight.Bold else FontWeight.Normal, modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp))
            if (currencyUiItem.isSelected){
                Icon(modifier = Modifier.size(24.dp), imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_filled) , contentDescription = null, tint = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun ProgressBar() {
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

@Composable
fun AppProgressDialog(title:String){
    Dialog(onDismissRequest = { }, DialogProperties(false, false)) {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier
            .fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    Modifier
                        .padding(start = 20.dp, end = 16.dp, top = 20.dp, bottom = 20.dp)
                        .size(40.dp),MaterialTheme.colors.primary,4.dp)
                Text(text = title, style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.onSurface)
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyItemUiPreview(){
    MaterialTheme {
        CurrencyUiItem(CurrencyItemUiState(
            "Indian Rupee","INR",true
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
                    "Indian Rupee","INR",it == 0
                ))
            }
        }
        CurrencyListScreen(items){}
    }
}