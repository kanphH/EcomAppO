package kan.kpo.ecomappo.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kan.kpo.ecomappo.ui.theme.EcomAppOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Home",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Blue
        ),
        actions = {
            IconButton(onClick = {onCartClick()}) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "ShoppingCart")
            }
            IconButton(onClick = {onProfileClick()}) {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Account")
            }
        }


    )

}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPrev() {
    EcomAppOTheme {

    }

}