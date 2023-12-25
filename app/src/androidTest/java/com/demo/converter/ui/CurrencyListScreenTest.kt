package com.demo.converter.ui

import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.converter.common.TestTag
import com.demo.converter.view.currency_list.CurrencyListScreen
import com.demo.converter.view.currency_list.CurrencyListUiState
import com.demo.converter.view.model.CurrencyItemUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyListScreenTest {

    @JvmField
    @Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validate_loading_state(){
        val uiState = CurrencyListUiState(isLoading = true)
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyListScreen(uiState = uiState, onClickCurrencyItem = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertExists()
        composeTestRule.onNodeWithTag(TestTag.CURRENCY_LIST).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.EMPTY_STATE).assertDoesNotExist()
    }

    @Test
    fun validate_empty_state(){
        val uiState = CurrencyListUiState(isLoading = false)
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyListScreen(uiState = uiState, onClickCurrencyItem = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.CURRENCY_LIST).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.EMPTY_STATE).assertExists()
    }

    @Test
    fun validate_progress_bar_state(){
        val uiState = CurrencyListUiState(isLoading = false, showProgressDialog = true)
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyListScreen(uiState = uiState, onClickCurrencyItem = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.CURRENCY_LIST).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.EMPTY_STATE).assertExists()
        composeTestRule.onNodeWithTag(TestTag.APP_PROGRESS_DIALOG).assertExists()
    }

    @Test
    fun validate_currency_list_state(){
        val uiState = CurrencyListUiState(currencyUiItems = listOf(CurrencyItemUiState("Indian Rupee","INR",false)))
        composeTestRule.setContent {
            MaterialTheme {
                CurrencyListScreen(uiState = uiState, onClickCurrencyItem = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTag.PROGRESS_LOADER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.CURRENCY_LIST).assertExists()
        composeTestRule.onNodeWithTag(TestTag.EMPTY_STATE).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTag.APP_PROGRESS_DIALOG).assertDoesNotExist()
    }
}