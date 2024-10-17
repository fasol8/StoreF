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

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products
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
}