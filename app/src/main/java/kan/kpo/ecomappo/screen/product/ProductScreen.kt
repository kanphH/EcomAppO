package kan.kpo.ecomappo.screen.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.viewmodel.CartViewModel
import kan.kpo.ecomappo.viewmodel.ProductViewModel


@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    categoryId: String,
    navController: NavController,
    productViewModel:ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    //fetch product from the viewModel
    LaunchedEffect(categoryId) {
        productViewModel.fetchProducts(categoryId)

    }

    // collect the product from viewmodel
    val products by productViewModel.products.collectAsState()

    //Display the Product
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Product of Category $categoryId",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )


        //if there is no product
        if (products.isEmpty()) {
            Text(
                text = "No Product Found",
                modifier = Modifier.padding(16.dp)
            )

        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products) { product ->
                    ProductItems(
                        product = product,
                        onAddToCart = { cartViewModel.addToCart(product) },
                        //navigate to ProductScreenDetail with the product Id
                        onClick = {
                            navController.navigate(
                                Screens.ProductDetails.createRoute(
                                    product.id
                                )
                            )
                        }) //RoomDataBase *Insert

                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun ProductScreenPrev() {
    ProductScreen(categoryId = "", navController = rememberNavController())

}