package com.example.kpmapp_with_di.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kpmapp_with_di.ui.about.AboutScreen
import com.example.kpmapp_with_di.ui.home.HomePage

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {

            HomePage(
                onAboutClick = {
                    navController.navigate(Screen.About.route)
                }
            )
        }

        composable(Screen.About.route) {

            AboutScreen(
                onUpButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}