package kan.kpo.ecomappo.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DiscountBadge(
    modifier: Modifier = Modifier,
    discountPercentage: Int,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFE91363))
    ){

    Text(text = "${discountPercentage}% OFF",
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        color = Color.White,
        fontWeight = FontWeight.Bold)
    }

}

@Preview
@Composable
private fun DiscountBadegePrev() {
    DiscountBadge(discountPercentage = 50)

}