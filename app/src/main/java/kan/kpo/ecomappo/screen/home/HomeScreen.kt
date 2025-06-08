package kan.kpo.ecomappo.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kan.kpo.ecomappo.model.Category
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.ui.theme.EcomAppOTheme

val categories: List<Category> = listOf(
    Category(1, "Food", "https://img.icons8.com/color/96/restaurant.png"),
    Category(2, "Drink", "https://img.icons8.com/color/96/coffee-cup.png"),
    Category(3, "Dessert", "https://img.icons8.com/color/96/cake.png"),
    Category(4, "Pizza", "https://img.icons8.com/color/96/pizza.png"),
    Category(5, "Burger", "https://img.icons8.com/color/96/hamburger.png")

)

val productList: List<Product> = listOf(
    Product("1", "Pizza", 10.0, "https://img.icons8.com/color/96/pizza.png"),
    Product("2", "Laptop", 110.0, "https://img.icons8.com/color/96/hamburger.png"),
    Product("3", "Dessert", 55.5, "https://img.icons8.com/color/96/cake.png"),
    Product("4", "Pizza", 552.5, "https://img.icons8.com/color/96/pizza.png"),
    Product("5", "Burger", 515.5, "https://img.icons8.com/color/96/hamburger.png")

)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { MyTopAppBar() },
        bottomBar = { BottomNavigation() },
    ) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            var searchQuery by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {//Search Logic}
                },
                modifier = Modifier.padding(16.dp)
            )
            //Search Section


            //Categories Section

            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title = "Categories",
                actionText = "See All"
            ) {

            }
            var selectedCategory by remember { mutableStateOf(0) }
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(categories) { index, category ->
                    Categories(
                        icon = category.iconUrl,
                        text = category.name,
                        isSelected = selectedCategory == index,
                        onClick = {
                            selectedCategory = index
                            // navigation logic
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Featured", actionText = "See all", onActionClick = {})

            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productList) { product ->
                    FeaturedProducts(
                        product = product,
                        onProductClick = {})

                }
            }
        }
    }





}


@Preview
@Composable
private fun HomeScreenPrev() {
    EcomAppOTheme {
        HomeScreen()
    }

}