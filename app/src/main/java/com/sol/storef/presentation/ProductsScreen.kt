package com.sol.storef.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductsScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp)
    ) {
        Text(text = products.toString())
    }
}