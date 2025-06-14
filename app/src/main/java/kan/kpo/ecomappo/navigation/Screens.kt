package kan.kpo.ecomappo.navigation

sealed class Screens(val route : String) {
    //define screen for navigation in Compose
    // Each object represents a screen in the app's nav graph
    object Cart : Screens("cart")
    object Profile : Screens("profile")
    object ProductDetails : Screens("product_details/{productId}"){
        fun createRoute(productId : String) = "product_details/$productId"

    }
    object ProductList : Screens("product_list/{categoryId}") {
        fun createRoute(categoryId: String) = "product_list/$categoryId"
    }

    object Category : Screens("category")
    object Home : Screens("home")


    object SignUpScreen : Screens("signup")
    object LoginScreen : Screens("login")



}