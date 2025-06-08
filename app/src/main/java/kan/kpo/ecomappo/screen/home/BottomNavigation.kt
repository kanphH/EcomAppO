package kan.kpo.ecomappo.screen.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    currentRoute: String = "Home", // รับ parameter แทนการ hardcode
    onItemClick: (String) -> Unit = {}, // callback สำหรับ navigation
) {
    val items = listOf(
        BottomNavItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = "Home",
            badgeCount = 3382
        ),
        BottomNavItem(
            title = "Search",
            icon = Icons.Default.Search,
            route = "Search",
            badgeCount = 0
        ),
        BottomNavItem(
            title = "Wishlist",
            icon = Icons.Default.Favorite,
            route = "Wishlist",
            badgeCount = 10
        ),
        BottomNavItem(
            title = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = "Cart",
            badgeCount = 5
        ),
        BottomNavItem(
            title = "Profile",
            icon = Icons.Default.Person,
            route = "Profile",
            badgeCount = 0
        )
    )

    NavigationBar(
        modifier = modifier.height(80.dp),
        tonalElevation = 10.dp,
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onItemClick(item.route) // เรียก callback
                },
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = if (item.badgeCount > 99) "99+" else item.badgeCount.toString()
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Preview
@Composable
private fun BottomPrev() {
    BottomNavigation()


}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0,
)