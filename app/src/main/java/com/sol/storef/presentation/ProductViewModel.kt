package com.sol.storef.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.storef.data.repository.ProductRepository
import com.sol.storef.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private val _productsCart = MutableLiveData<List<Product>>(emptyList())
    val productsCart: LiveData<List<Product>> = _productsCart

    var limit = 10

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getAllProducts(limit)
                _products.value = response
                limit += 10
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun addProductsCart(product: Product) {
        val currentCart = _productsCart.value ?: emptyList()
        _productsCart.value = currentCart.plus(product)
    }

    fun deleteProducts(product: Product) {
        _productsCart.value = _productsCart.value?.filter { it.id != product.id }
    }

    fun getTotalCart(): Double {
        var total = 0.0
        _productsCart.value?.let { products ->
            total = products.sumOf { it.price }
        }
        return total
    }
}