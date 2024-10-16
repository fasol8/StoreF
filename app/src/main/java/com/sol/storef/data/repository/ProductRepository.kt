package com.sol.storef.data.repository

import com.sol.storef.data.network.FakeStoreApi
import com.sol.storef.domain.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: FakeStoreApi) {

    suspend fun getAllProducts(limit: Int): List<Product> {
        return api.getAllProducts(limit)
    }
}