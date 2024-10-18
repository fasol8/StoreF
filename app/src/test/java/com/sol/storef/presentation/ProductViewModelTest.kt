package com.sol.storef.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sol.storef.data.repository.ProductRepository
import com.sol.storef.domain.model.Product
import com.sol.storef.domain.model.Rating
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set main dispatcher for coroutines
        viewModel = ProductViewModel(repository)
    }

    @Test
    fun `addProductsCart should add product to cart`() {
        // Mock product
        val product = Product("category1", "description1", 1, "image1", 10.0, Rating(100, 4.0), "title1")

        // Add product to cart
        viewModel.addProductsCart(product)

        // Assert the product is in the cart
        assertEquals(listOf(product), viewModel.productsCart.value)
    }

    @Test
    fun `getTotalCart should return correct total`() {
        // Mock products
        val product1 = Product("category1", "description1", 1, "image1", 10.0, Rating(100, 4.0), "title1")
        val product2 = Product("category2", "description2", 2, "image2", 20.0, Rating(200, 4.5), "title2")

        // Add products to cart
        viewModel.addProductsCart(product1)
        viewModel.addProductsCart(product2)

        // Assert total price
        assertEquals(30.0, viewModel.getTotalCart(), 0.0)
    }
}
