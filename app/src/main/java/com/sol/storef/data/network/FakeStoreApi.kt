package com.sol.storef.data.network

import com.sol.storef.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface FakeStoreApi {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int = 10,
    ): List<Product>
}