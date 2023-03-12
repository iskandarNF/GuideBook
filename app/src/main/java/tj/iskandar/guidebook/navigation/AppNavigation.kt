package tj.iskandar.guidebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
}

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    navDestination: NavDestination?,
    restartActivity: () -> Unit,

) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen.route

    ) {
        omdb(navController = navHostController,
            restartActivity = {
                restartActivity() })

    }
}