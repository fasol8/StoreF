package com.sol.storef.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sol.storef.presentation.CartScreen
import com.sol.storef.presentation.ProductViewModel
import com.sol.storef.presentation.ProductsScreen

sealed class StoreF(val route: String, val title: String) {
    data object Products : StoreF("productsScreen", "Products Screen")
    data object Cart : StoreF("cartScreen", "Cart Screen")
}

@Composable
fun StoreNavHost() {
    val navController = rememberNavController()
    val viewModel: ProductViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = StoreF.Products.route) {

        composable(StoreF.Products.route) {
            ProductsScreen(navController, viewModel)
        }
        composable(StoreF.Cart.route) {
            CartScreen(viewModel)
        }
    }
}