package kan.kpo.ecomappo.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(modifier: Modifier = Modifier,
                  onSignOut:() ->  Unit) {


}

@Preview
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(modifier = Modifier, onSignOut = {})

}