package kan.kpo.ecomappo.repository

import android.util.Log
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.room.CartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Acts as a bridge between the ViewModel and the room database via the Dao.
class CartRepository @Inject constructor(private val cartDao: CartDao) {

    val allCartItems: Flow<List<Product>> = cartDao.getAllCartItems()

    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemById(product.id)

        //  เพิ่มจำนวนแทน
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.upsertCartItem(updatedItem)
            Log.v("TAGY", "Updated quantity: ${updatedItem.quantity}")
        } else {
            cartDao.upsertCartItem(product)
            Log.v("TAGY", "Adding new item: $product")
        }
    }

    //    เพิ่ม error handling (quantity <= 0 จะลบ item)
    suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteCartItemById(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }

    suspend fun removeFromCart(product: Product) {
        cartDao.deleteCartItem(product)
    }






}