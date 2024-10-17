package com.sol.storef.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sol.storef.R
import com.sol.storef.domain.model.Product
import com.sol.storef.navigation.StoreF

@Composable
fun ProductsScreen(navController: NavController, viewModel: ProductViewModel) {
    val products by viewModel.products.observeAsState(emptyList())
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp, top = 60.dp)
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(products.size) { index ->
                val product = products[index]
                CardProduct(product, viewModel) {
                    selectedProduct = product
                }

                if (index == products.size - 1) {
                    LaunchedEffect(key1 = true) {
                        viewModel.getAllProducts()
                    }
                }
            }

        }
        selectedProduct?.let { product ->
            AlertProduct(product, viewModel, navController) {
                selectedProduct = null // Cierra el diálogo
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(StoreF.Cart.route)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = "Shopping cart"
            )
        }
    }
}

@Composable
fun AlertProduct(
    product: Product,
    viewModel: ProductViewModel,
    navController: NavController,
    onDismiss: () -> Unit
) {
    AlertDialog(title = { Text(text = product.title) },
        text = {
            Column {
                AsyncImage(model = product.image, contentDescription = product.image)
                Text(text = product.category, style = MaterialTheme.typography.bodySmall)
                Text(text = product.description)
                Text(text = product.price.toString())
                Text(text = product.rating.rate.toString())
            }
        },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.addProductsCart(product)
                navController.navigate(StoreF.Cart.route)
            }) {
                Text(text = "Add to cart")
            }
        })
}

@Composable
fun CardProduct(product: Product, viewModel: ProductViewModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onClick() }
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.height(250.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = product.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Row {
                Text(
                    text = product.price.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { viewModel.addProductsCart(product) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_shopping_cart),
                        contentDescription = "add product cart"
                    )
                }
            }
        }
    }
}
