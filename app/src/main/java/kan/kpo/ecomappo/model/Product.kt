package kan.kpo.ecomappo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class Product(
    @PrimaryKey
    val id : String ="",
    val name : String = "",
    val price : Double = 0.0,
    val imageUrl : String = "",
    val categoryId : String? = null,
    val quantity : Int = 1
)
