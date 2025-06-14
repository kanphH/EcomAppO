package kan.kpo.ecomappo.viewmodel

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.repository.CartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
) : ViewModel() {

    val cartItems = repository.allCartItems

    fun addToCart(product: Product) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productId, quantity)
        }
    }

    fun calculateToTal(items: List<Product>): Double {
        return items.sumOf { item ->
            item.price * item.quantity
        }
    }

    // เพิ่มจำนวน +1 (ปุ่ม + ใน Cart)
    fun increaseQuantity(product: Product) {
        updateQuantity(product.id, product.quantity + 1)
    }

    // ลดจำนวน -1 (ปุ่ม - ใน Cart)
    fun decreaseQuantity(product: Product) {
        updateQuantity(product.id, product.quantity - 1)
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            repository.removeFromCart(product)
        }
    }
}




