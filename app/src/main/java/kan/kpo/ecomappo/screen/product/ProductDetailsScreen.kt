package kan.kpo.ecomappo.screen.product

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import kan.kpo.ecomappo.R
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.navigation.Screens.ProductDetails
import kan.kpo.ecomappo.viewmodel.CartViewModel
import kan.kpo.ecomappo.viewmodel.ProductDetaislViewModel
import kan.kpo.ecomappo.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavController,
    productViewModel: ProductDetaislViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {

    LaunchedEffect(productId) {
        productViewModel.fetchProductDetails(productId)
    }
    val productState = productViewModel.product.collectAsState()
    val product = productState.value


    if (product == null) {
        Text(text = "Product Not Found")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Product Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    placeholder = painterResource(R.drawable.ic_loading),
                    error = painterResource(R.drawable.ic_error),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "฿${product.price}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = product.categoryId ?: "Category Not Found",
                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {


                Button(
                    onClick = {
                        cartViewModel.addToCart(product)
                        navController.navigate(Screens.Cart.route)
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to Cart",
                        modifier = Modifier.size(18.dp)
                    )

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun ProductDetailsScreenPreview() {
    ProductDetailsScreen(productId = "", navController = rememberNavController())


}