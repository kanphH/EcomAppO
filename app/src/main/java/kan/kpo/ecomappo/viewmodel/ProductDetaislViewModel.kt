package kan.kpo.ecomappo.viewmodel

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
class ProductDetaislViewModel @Inject constructor(private val repository: FirestoreRepository) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                val product = repository.getProductById(productId)
                _product.value = product
            } catch (e: Exception) {
                // Handle error
                println("Error fetching product details: ${e.message}")
            }

        }
    }

}