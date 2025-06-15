package kan.kpo.ecomappo.screen.cart

import android.R.attr.top
import android.util.Log.i
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.screen.home.CustomButton
import kan.kpo.ecomappo.viewmodel.CartViewModel


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val cartItem by cartViewModel.cartItems.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (cartItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Your cart is empty")
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    navController.navigate(Screens.Home.route)
                }) {
                    Text(text = "Continue Shopping")
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItem) { item ->
                    CartItemCard(
                        item = item,
                        onRemoveItem = { cartViewModel.removeFromCart(item) },
                        onIncreaseQuantity = { cartViewModel.increaseQuantity(item) },
                        onDecreaseQuantity = {
                            if (item.quantity > 1) {
                                cartViewModel.decreaseQuantity(item)
                            }
                        }
                    )
                }
            }

            // Checkout Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "฿${cartViewModel.calculateToTal(cartItem)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                CustomButton(
                    modifier = Modifier,
                    text = "Checkout",
                    icon = Icons.Default.ShoppingCart,
                    onClick = {navController.navigate(Screens.Home.route)}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPrev() {

}