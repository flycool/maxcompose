package com.example.maxcompose.compose2.navigation

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NestNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("about") {

        }
        navigation(
            startDestination = "login",
            route = "auth"
        ) {
            composable(route = "login") {
                val shareViewModel = it.shareViewModel<SimpleViewModel>(navController = navController)
                Button(onClick = {
                    navController.navigate("calender") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                }) {

                }
            }
            composable(route = "register") {
                val shareViewModel = it.shareViewModel<SimpleViewModel>(navController = navController)
            }
            composable(route = "forgot_password") {
                val shareViewModel = it.shareViewModel<SimpleViewModel>(navController = navController)
            }
        }
        navigation(startDestination = "calender_overview", route = "calender") {
            composable("calender_overview") {

            }
            composable("calender_entry") {

            }
        }

    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.shareViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}