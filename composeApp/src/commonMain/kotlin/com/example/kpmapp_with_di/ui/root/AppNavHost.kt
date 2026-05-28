package com.example.kpmapp_with_di.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kpmapp_with_di.ui.about.AboutScreen
import com.example.kpmapp_with_di.ui.home.HomePage
import com.example.kpmapp_with_di.ui.reminders.RemindersScreen

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
                onAboutClick = { navController.navigate(Screen.About.route) },
                onRemindersClick = { navController.navigate(Screen.Reminders.route) }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(
                onUpButtonClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Reminders.route) {
            RemindersScreen(
                onUpButtonClick = { navController.popBackStack() }
            )
        }
    }
}