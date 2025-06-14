package kan.kpo.ecomappo.screen.cart

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import kan.kpo.ecomappo.R
import kan.kpo.ecomappo.model.Product

@Composable
fun CartItemCard(modifier: Modifier = Modifier,
                 item : Product,
                 onRemoveItem : () -> Unit) {
    Card (modifier = Modifier.fillMaxWidth()
        .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier.size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "$${item.price}", style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp,
                    )

            }
           Column (horizontalAlignment = Alignment.CenterHorizontally){
               Row (verticalAlignment = Alignment.CenterVertically,
                   ){



               }
               IconButton(onClick = {onRemoveItem()}) {
                   Image(imageVector = Icons.Default.Delete,
                       contentDescription = "Remove Item")
               }



           }
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun CartItemCardprev() {
    CartItemCard(modifier = Modifier, item = Product(
        "1", "Pizza", 10.0, "https://img.icons8.com/color/96/pizza.png"
    ), onRemoveItem = {})

}