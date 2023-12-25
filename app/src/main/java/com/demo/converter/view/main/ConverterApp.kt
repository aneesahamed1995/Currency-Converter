package com.demo.converter.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.converter.common.AppConstant
import com.demo.converter.common.BundleProperty
import com.demo.converter.view.convertion.ConverterViewModel
import com.demo.converter.view.convertion.CurrencyConversionScreen
import com.demo.converter.view.currency_list.CurrencyListScreen
import com.demo.converter.view.navigation.NavArgName
import com.demo.converter.view.navigation.NavScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ConverterApp(navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavScreen.entries.find { it.route == backStackEntry?.destination?.route } ?: NavScreen.Start
    Scaffold(
        topBar = {
            ConverterAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Start.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = NavScreen.Start.route){ backStackEntry->
                val viewModel: ConverterViewModel = koinViewModel()
                 backStackEntry.savedStateHandle.get<String>(BundleProperty.SELECTED_CURRENCY_CODE)?.let { selectedCurrencyCode->
                     viewModel.baseCurrencyCode = selectedCurrencyCode
                     backStackEntry.savedStateHandle.remove<String>(BundleProperty.SELECTED_CURRENCY_CODE)
                }
                CurrencyConversionScreen(viewModel = viewModel, onClickBaseCurrency = { baseCode->
                    navController.navigate(NavScreen.CurrencyList.getRouteWithArg(arrayOf(baseCode)))
                })
            }
            composable(NavScreen.CurrencyList.route, listOf(navArgument(NavArgName.BASE_CURRENCY) {
                defaultValue = AppConstant.STRING_EMPTY
                type = NavType.StringType
            })) { backStackEntry->
                val selectedCurrencyCode = backStackEntry.arguments?.getString(NavArgName.BASE_CURRENCY)?:AppConstant.STRING_EMPTY
                CurrencyListScreen(viewModel = koinViewModel{ parametersOf(selectedCurrencyCode) }, onSelectedCurrencyItem = { currencyCode->
                    navController.previousBackStackEntry?.savedStateHandle?.set(BundleProperty.SELECTED_CURRENCY_CODE,currencyCode)
                    navController.popBackStack()
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterAppBar(
    currentScreen: NavScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
