package com.sol.storef.data.repository

import com.sol.storef.data.network.FakeStoreApi
import com.sol.storef.domain.model.Product
import com.sol.storef.domain.model.Rating
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {

    @Mock
    private lateinit var api: FakeStoreApi

    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        repository = ProductRepository(api)
    }

    @Test
    fun `getAllProducts should return list of products`() = runBlocking {
        // Mock data
        val mockProducts = listOf(
            Product("category1", "description1", 1, "image1", 10.0, Rating(100, 4.0), "title1"),
            Product("category2", "description2", 2, "image2", 20.0, Rating(200, 4.5), "title2")
        )

        // Mock API response
        `when`(api.getAllProducts(10)).thenReturn(mockProducts)

        // Call the repository function
        val result = repository.getAllProducts(10)

        // Verify and assert
        verify(api).getAllProducts(10)
        assertEquals(mockProducts, result)
    }
}
