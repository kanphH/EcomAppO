package kan.kpo.ecomappo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: FirestoreRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    fun fetchProducts(categoryId: String) {
        viewModelScope.launch {
            try {
           val products = repository.getProductByCategory(categoryId)
                _products.value = products

            } catch (e: Exception) {
                Log.e("TAGY", "Error fetching products: ${e.message}")

            }

        }

    }

    fun getAllProductInFirestore() {
        viewModelScope.launch {
            try {
                val allProducts = repository.getAllProductInFirestore()
                _allProducts.value = allProducts
            } catch (e: Exception){
                Log.e("TAGY", "Error fetching products: ${e.message}")

            }

        }

    }



}