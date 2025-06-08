package kan.kpo.ecomappo.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kan.kpo.ecomappo.ui.theme.CategoryColor

@Composable
fun Categories(
    modifier: Modifier = Modifier,
    icon: String,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CategoryColor
            else Color.LightGray.copy(0.5f)
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = Color.LightGray.copy(0.5f)
        ),
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(painter = rememberVectorPainter(image = Icons.Default.ShoppingCart),
                contentDescription = null,
                tint = if (isSelected) Color.White else Color.Black,
                modifier = Modifier.size(10.dp))
        Text(text,
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp))

        }

    }


}

@Preview
@Composable
private fun CategoriesPrev() {
    Categories(modifier = Modifier, icon = "qui", text = "veri", isSelected = false, onClick = {})

}