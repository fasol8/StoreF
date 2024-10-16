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

    init {
        getAllProducts()
    }

    fun getAllProducts(limit: Int = 10) {
        viewModelScope.launch {
            try {
                val response = repository.getAllProducts(10)
                _products.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}