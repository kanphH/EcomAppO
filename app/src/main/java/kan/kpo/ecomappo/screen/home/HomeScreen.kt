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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kan.kpo.ecomappo.model.Category
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.ui.theme.EcomAppOTheme
import kan.kpo.ecomappo.viewmodel.CategoryViewModel
import kan.kpo.ecomappo.viewmodel.ProductViewModel
import kan.kpo.ecomappo.viewmodel.SearchViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),

) {


    // ✅ ใช้ LaunchedEffect แทน
    LaunchedEffect(Unit) {
        productViewModel.getAllProductInFirestore()
    }
    val focusManager = LocalFocusManager.current



    Scaffold(
        topBar = { MyTopAppBar(onCartClick = onCartClick, onProfileClick = onProfileClick) },
        bottomBar = { BottomNavigation(navController = navController) },
    ) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            var searchQuery by remember { mutableStateOf("") }
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { searchViewModel.searchProduct(searchQuery)
                           focusManager.clearFocus()},
                modifier = Modifier.padding(16.dp)
            )

            if (searchQuery.isNotBlank()){
                SearchResult(
                    navController = navController)

            }

            // Categories Section
            val categories by categoryViewModel.categories.collectAsState()
            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title = "Categories",
                actionText = "See All"
            ) {
                navController.navigate(Screens.Category.route)
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
                            // ✅ ส่ง category.id แทน index
                            navController.navigate(Screens.ProductList.createRoute(category.id.toString()))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(
                title = "Featured",
                actionText = "See all",
                onActionClick = {
                    navController.navigate(Screens.Category.route)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val allProduct by productViewModel.allProducts.collectAsState()

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allProduct) { product ->
                    FeaturedProducts(
                        product = product,
                        onProductClick = {
                            navController.navigate(Screens.ProductDetails.createRoute(product.id))
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPrev() {
    EcomAppOTheme {
        HomeScreen(navController = rememberNavController(),
            onCartClick = {},onProfileClick = {})
    }

}