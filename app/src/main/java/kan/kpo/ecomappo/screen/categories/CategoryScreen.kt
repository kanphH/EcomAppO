package kan.kpo.ecomappo.screen.categories

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kan.kpo.ecomappo.model.Category
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.viewmodel.CategoryViewModel


@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onCategoryClick: () -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val categories by categoryViewModel.categories.collectAsState()

    Column {
        if (categories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Category Found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        } else {
            //Category title
            Text(
                text = "Category :",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
            )

            //Category Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(22.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                horizontalArrangement = Arrangement.spacedBy(22.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {
                            navController.navigate(Screens.ProductList.createRoute(category.id.toString()))

                        })

                }

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun CategoryScreenPrev() {
    CategoryScreen(navController = rememberNavController(), onCategoryClick = {})

}