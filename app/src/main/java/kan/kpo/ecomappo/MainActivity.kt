package kan.kpo.ecomappo

import android.os.Bundle
import android.util.Log
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

                //AuthViewModel - ใช้ single instance
                val authViewModel: AuthViewModel = hiltViewModel()

                // properly observe the authentication state
                val authState by authViewModel.authState.collectAsState()
                val currentUser by authViewModel.currentUser.collectAsState()

                // เช็คจาก currentUser แทน authState
                val isLoggedIn by remember {
                    derivedStateOf { currentUser != null }
                }

                // Debug logging
                LaunchedEffect(authState, currentUser) {
                    Log.d("MainActivity", "Auth state: $authState")
                    Log.d("MainActivity", "Current user: ${currentUser?.email}")
                    Log.d("MainActivity", "Is logged in: $isLoggedIn")
                }

                NavHost(navController = navController, startDestination = Screens.Home.route) {
                    composable(Screens.Home.route) {
                        HomeScreen(
                            navController = navController,
                            onProfileClick = {
                                Log.d("MainActivity", "Profile click - isLoggedIn: $isLoggedIn")
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
                        // ตรวจสอบว่า user login อยู่หรือไม่
                        if (isLoggedIn && currentUser != null) {
                            ProfileScreen(
                                navController = navController,
                                onSignOut = {
                                    Log.d("MainActivity", "Sign out initiated")
                                    authViewModel.signOut()
                                    navController.navigate(Screens.Home.route) {
                                        popUpTo(Screens.Home.route) {
                                            inclusive = false
                                        }
                                    }
                                }
                            )
                        } else {
                            // ถ้าไม่ login อยู่ ให้ไปหน้า login
                            LaunchedEffect(Unit) {
                                Log.d("MainActivity", "User not logged in, navigating to login")
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
                            onSignUpSuccess = {
                                Log.d("MainActivity", "Sign up success, navigating to home")
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(Screens.SignUpScreen.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            onSignUpError = {},
                        )
                    }

                    composable(Screens.LoginScreen.route) {
                        LoginScreen(
                            navController = navController,
                            onNavigateToSignUp = { navController.navigate(Screens.SignUpScreen.route) },
                            onLoginSuccess = {
                                Log.d("MainActivity", "Login success, navigating to home")
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(Screens.LoginScreen.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            onLoginError = {}
                        )
                    }
                }
            }
        }
    }
}