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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.demo.converter.R
import com.demo.converter.common.TestTag
import com.demo.converter.view.common.AppProgressDialog
import com.demo.converter.view.common.ProgressBar
import com.demo.converter.view.common.showErrorMessage
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
    if (uiState.error != null){
        showErrorMessage(LocalContext.current,uiState.error)
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
            uiState.currencyUiItems.isNotEmpty()-> CurrencyListScreen(uiState.currencyUiItems,onClickCurrencyItem)
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
    LazyColumn(contentPadding = PaddingValues(all = 16.dp),verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.testTag(TestTag.CURRENCY_LIST)){
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
            .clickable { onClickCurrencyItem(currencyUiItem) }.testTag(TestTag.CURRENCY_ITEM),
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
fun EmptyStateMessage(message:String) {
    Box(
        modifier = Modifier.fillMaxSize().testTag(TestTag.EMPTY_STATE),
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