package com.sol.storef.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.storef.domain.model.Product

@Composable
fun ProductsScreen(viewModel: ProductViewModel = hiltViewModel()) {
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
                CardProduct(product) {
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
            AlertProduct(product) {
                selectedProduct = null // Cierra el diálogo
            }
        }
//        Text(text = products.toString())
    }
}

@Composable
fun AlertProduct(product: Product, onDismiss: () -> Unit) {
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
        onDismissRequest = {
//            Button(onClick = {
//                Log.i("Confirm", "Add card $product")
////            val intent = Intent()
////            context.startActivity(intent)
//            }) {
//                Text(text = "Add a cart")
//            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        })
}

@Composable
fun CardProduct(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { onClick() }
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.height(250.dp)
            )
            Text(text = product.title, style = MaterialTheme.typography.titleSmall)
            Text(text = product.price.toString(), style = MaterialTheme.typography.displaySmall)
        }
    }
}
