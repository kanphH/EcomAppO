package kan.kpo.ecomappo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kan.kpo.ecomappo.screen.cart.CartScreen
import kan.kpo.ecomappo.screen.home.HomeScreen
import kan.kpo.ecomappo.ui.theme.EcomAppOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcomAppOTheme {
                CartScreen()
            }
        }
    }
}

