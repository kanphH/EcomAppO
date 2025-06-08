package kan.kpo.ecomappo.screen.cart

import android.R.attr.top
import android.util.Log.i
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.screen.cart.cartItem


val cartItem = listOf(
    Product(
        "1", "Pizza", 10.0, "https://img.icons8.com/color/96/pizza.png"
    ),
    Product(
        "2", "Laptop", 110.0, "https://img.icons8.com/color/96/hamburger.png"
    ),
    Product(
        "3", "Dessert", 55.5, "https://img.icons8.com/color/96/cake.png"
    ),


    )


@Composable
fun CartScreen(modifier: Modifier = Modifier) {
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

                Button(onClick = {}) {
                    Text(text = "Continue Shopping")
                }

            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItem) { items ->
                    CartItemCard(
                        item = items,
                        onRemoveItem = {/* Remove Item Logic */ }
                    )


                }
            }

            // checkout Section

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
                    // call viewModel to get Price
                    Text(
                        text = "$1000",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                var isPressed by remember { mutableStateOf(false) }

                Button(
                    onClick = { /* Checkout Logic */ },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .scale(if (isPressed) 0.95f else 1f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                }
                            )
                        },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BCD4)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        )
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Text(
                        text = "Checkout",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun CartScreenPrev() {
    CartScreen()

}