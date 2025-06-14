package kan.kpo.ecomappo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kan.kpo.ecomappo.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
// DAO: for handling operations related to a cart
// table/entity, adding items to cart, retrieving cart
// contents, deleting items...
interface CartDao{

    @Upsert
    suspend fun upsertCartItem(cartItem: Product)

    @Update
    suspend fun updateCartItem(cartItem: Product)

    @Delete
    suspend fun deleteCartItem(cartItem: Product)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: String, quantity: Int)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems() : Flow<List<Product>>

    @Query("SELECT * FROM cart_items WHERE id = :productId")
    suspend fun getCartItemById(productId : String) : Product?

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun deleteCartItemById(productId: String)



}