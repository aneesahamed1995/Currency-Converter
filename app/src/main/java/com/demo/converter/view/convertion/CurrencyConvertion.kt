package com.demo.converter.view.convertion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.demo.converter.R
import com.demo.converter.common.TestTag
import com.demo.converter.common.extension.noRippleClickable
import com.demo.converter.view.common.ProgressBar
import com.demo.converter.view.model.CurrencyConversionItemUiState


@Composable
fun CurrencyConversionScreen(viewModel: ConverterViewModel, onClickBaseCurrency:(baseCode:String)->Unit){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    CurrencyConversionScreen(uiState, onInputChanged = { viewModel.updateAmount(it) },onClickBaseCurrency)
    LaunchedEffect(Unit){
        viewModel.refreshConversion()
        if (viewModel.hasBaseCurrency){
            viewModel.syncCurrentExchangeRate()
        }
    }
}

@Composable
fun CurrencyConversionScreen(uiState:CurrencyConversionUiState, onInputChanged:(amount:Double)->Unit, onClickBaseCurrency:(baseCode:String)->Unit){
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)) {
        Column {
            Card(Modifier.fillMaxWidth(), elevation = 2.dp, shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(16.dp), bottomStart = CornerSize(16.dp))) {
                Column(Modifier.padding(16.dp)) {
                    var fieldInput by rememberSaveable { mutableStateOf("") }
                    val pattern = remember { Regex("^\\d*\\.?\\d*\$") }
                    OutlinedTextField(value = fieldInput, onValueChange = { text->
                        if (text.trim().isEmpty() || !(text.length == 1 && text == ".") && text.matches(pattern)){
                            fieldInput = text
                            onInputChanged((fieldInput.ifEmpty { "0.0" }).toDouble())
                        }
                    },
                        textStyle = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Row(
                        Modifier
                            .padding(top = 12.dp)
                            .border(
                                0.5.dp,
                                MaterialTheme.colors.onSurface,
                                MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .align(Alignment.End)
                            .noRippleClickable { onClickBaseCurrency(uiState.baseCurrencyCode) }.testTag(TestTag.SELECTED_CURRENCY_CODE), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = uiState.baseCurrencyCode.ifEmpty { stringResource(id = R.string.select) }, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle1, modifier = Modifier.testTag(TestTag.SELECTED_CURRENCY_CODE_TEXT))
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_drop_down), modifier = Modifier.padding(start = 8.dp), contentDescription = null)
                    }
                }
            }
            Box {
                when{
                    uiState.conversionUiItems.isNotEmpty()->CurrencyConversionContent(uiState.conversionUiItems)
                    uiState.showExchangeRateLoading-> ProgressBar()
                }
            }
        }
    }
}

@Composable
fun CurrencyConversionContent(uiItems:List<CurrencyConversionItemUiState>){
    Column(Modifier.fillMaxWidth().testTag(TestTag.CURRENCY_CONVERSION_LIST)) {
        Text(
            text = stringResource(R.string.currencies),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6
        )
        LazyColumn(contentPadding = PaddingValues(all = 16.dp),verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(items  = uiItems, key = { item-> item.code }){ item-> CurrencyItem(item) }
        }
    }
}

@Composable
private fun CurrencyItem(uiItem:CurrencyConversionItemUiState){
    Card(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp), elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                Modifier.weight(1f)) {
                Text(text = uiItem.code, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
                Text(text = uiItem.name,fontWeight = FontWeight.Light, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.subtitle2)
            }
            Column(Modifier.weight(1f)) {
                Text(text = uiItem.amount.toString(), modifier = Modifier.align(Alignment.End), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
                Text(text = stringResource(id = R.string.exchange_rate_placeholder, formatArgs = arrayOf(uiItem.baseCode,uiItem.exchangeRate,uiItem.code)),modifier = Modifier.align(Alignment.End), fontWeight = FontWeight.Light, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CurrencyConversionPreView(){
    val items = mutableListOf<CurrencyConversionItemUiState>().also { list->
        repeat(10){
            list.add(CurrencyConversionItemUiState(
                "Euro",
                "EUR",
                "INR",
                0.83,
                2.0,
                100.0
            ))
        }
    }
    MaterialTheme { CurrencyConversionScreen(CurrencyConversionUiState(conversionUiItems = items),{},{}) }
}

@Preview
@Composable
private fun CurrencyItemPreview(){
    MaterialTheme {
        CurrencyItem(CurrencyConversionItemUiState(
            "Euro",
             "EUR",
            "INR",
            0.83,
             2.0,
            200.0
        ))
    }
}


