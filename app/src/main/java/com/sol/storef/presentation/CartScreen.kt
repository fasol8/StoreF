package com.sol.storef.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sol.storef.domain.model.Product

@Composable
fun CartScreen(viewModel: ProductViewModel) {
    val cart by viewModel.productsCart.observeAsState(emptyList())
    val total = remember(cart) { viewModel.getTotalCart() }

    Box(modifier = Modifier.padding(4.dp, top = 60.dp)) {
        Log.i("Cart screen", cart.toString())
        Column {
            LazyColumn {
                cart?.let {
                    items(it.size) { index ->
                        val product = cart!![index]
                        ProductCart(product, viewModel)

                    }
                }
            }
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                Text(
                    text = "Total: $${total}",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

        }
    }
}

@Composable
fun ProductCart(product: Product, viewModel: ProductViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.height(100.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.title, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Delete",
                    modifier = Modifier.clickable { viewModel.deleteProducts(product) },
                    color = Color.Blue
                )
            }
            Text(text = product.price.toString(), modifier = Modifier.padding(end = 16.dp))
        }
    }
}
