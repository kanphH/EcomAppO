package kan.kpo.ecomappo.model

data class Product(
    val id : String ="",
    val name : String = "",
    val price : Double = 0.0,
    val imageUrl : String = "",
    val categoryId : String = "",
)
