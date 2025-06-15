package kan.kpo.ecomappo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kan.kpo.ecomappo.navigation.Screens
import kan.kpo.ecomappo.profile.LoginScreen
import kan.kpo.ecomappo.profile.SignUpScreen
import kan.kpo.ecomappo.screen.cart.CartScreen
import kan.kpo.ecomappo.screen.categories.CategoryScreen

import kan.kpo.ecomappo.screen.home.HomeScreen
import kan.kpo.ecomappo.screen.product.ProductDetailsScreen
import kan.kpo.ecomappo.screen.product.ProductScreen
import kan.kpo.ecomappo.screen.profile.ProfileScreen
import kan.kpo.ecomappo.ui.theme.EcomAppOTheme
import kan.kpo.ecomappo.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcomAppOTheme {
                // Navigation System
                val navController = rememberNavController()

                //AuthViewModel
                val authViewModel: AuthViewModel = hiltViewModel()

                // properly observe the authentication state
                val authState by authViewModel.authState.collectAsState()
                val isLoggedIn = authState is AuthViewModel.AuthState.Success

                NavHost(navController = navController, startDestination = Screens.Home.route) {
                    composable(Screens.Home.route) {
                        HomeScreen(
                            navController = navController,
                            onProfileClick = {
                                if(isLoggedIn) {
                                    navController.navigate(Screens.Profile.route)
                                } else {
                                    navController.navigate(Screens.LoginScreen.route)
                                }
                            },
                            onCartClick = { navController.navigate(Screens.Cart.route) }
                        )
                    }

                    composable(Screens.Cart.route) {
                        CartScreen(navController = navController)
                    }

                    composable(Screens.Profile.route) {
                        val currentUser by authViewModel.currentUser.collectAsState()

                        if (currentUser != null) {
                            ProfileScreen(
                                navController = navController,
                                onSignOut = {
                                    authViewModel.signOut()
                                    navController.navigate(Screens.LoginScreen.route) {
                                        popUpTo(Screens.Home.route) {
                                            inclusive = false
                                        }
                                    }
                                }
                            )
                        } else {
                            LaunchedEffect(Unit) {
                                navController.navigate(Screens.LoginScreen.route) {
                                    popUpTo(Screens.Home.route) {
                                        inclusive = false
                                    }
                                }
                            }
                        }
                    }

                    composable(Screens.Category.route) {
                        CategoryScreen(
                            navController = navController,
                            onCategoryClick = { Screens.Cart.route }
                        )
                    }

                    composable(Screens.ProductDetails.route) {
                        val productId = it.arguments?.getString("productId")
                        if (productId != null) {
                            ProductDetailsScreen(
                                productId = productId,
                                navController = navController
                            )
                        }
                    }

                    composable(Screens.ProductList.route) {
                        val categoryId = it.arguments?.getString("categoryId")
                        if (categoryId != null) {
                            ProductScreen(
                                categoryId = categoryId,
                                navController = navController
                            )
                        }
                    }

                    composable(Screens.SignUpScreen.route) {
                        SignUpScreen(
                            navController = navController,
                            onNavigateToLogin = { navController.navigate(Screens.LoginScreen.route) },
                            onSignUpSuccess = { navController.navigate(Screens.Home.route) },
                            onSignUpError = {},
                        )
                    }

                    composable(Screens.LoginScreen.route) {
                        LoginScreen(
                            navController = navController,
                            onNavigateToSignUp = { navController.navigate(Screens.SignUpScreen.route) },
                            onLoginSuccess = { navController.navigate(Screens.Home.route) },
                            onLoginError = {}
                        )
                    }
                }
            }
        }
    }
}