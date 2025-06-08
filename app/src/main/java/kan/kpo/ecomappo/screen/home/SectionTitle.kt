package kan.kpo.ecomappo.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kan.kpo.ecomappo.ui.theme.PrimaryColor

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        (
            Text(
                text = actionText, style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                modifier = Modifier.clickable {
                    onActionClick()
                }
            )
        )


    }

}

@Preview(showBackground = true)
@Composable
private fun SectionPrev() {
    SectionTitle(modifier = Modifier, title = "Categories", actionText = "See All", onActionClick = {})

}