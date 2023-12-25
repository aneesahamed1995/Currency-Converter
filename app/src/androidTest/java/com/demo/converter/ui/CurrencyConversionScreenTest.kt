package com.demo.converter.ui

import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.converter.R
import com.demo.converter.common.TestTag
import com.demo.converter.view.convertion.CurrencyConversionScreen
import com.demo.converter.view.convertion.CurrencyConversionUiState
import com.demo.converter.view.model.CurrencyConversionItemUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyConversionScreenTest {

    @JvmField
    @Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validate_loading_state(){
        val uiState = CurrencyConversionUiState(showExchangeRateLoading = true)
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyConversionScreen(uiState,{},{})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.SELECTED_CURRENCY_CODE).assertHasClickAction()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.select)).assertExists()
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertExists()
    }


    @Test
    fun validate_conversion_loaded_state(){
        val uiState = CurrencyConversionUiState(showExchangeRateLoading = false, conversionUiItems = listOf(
            CurrencyConversionItemUiState("Indian Rupee","INR","USD",83.0,10.0,100.0)
        ),"USD")
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyConversionScreen(uiState,{},{})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.SELECTED_CURRENCY_CODE).assertHasClickAction()
        composeTestRule.onNodeWithTag(TestTag.CURRENCY_CONVERSION_LIST).assertExists()
        composeTestRule.onNodeWithText("USD").assertExists()
    }

}