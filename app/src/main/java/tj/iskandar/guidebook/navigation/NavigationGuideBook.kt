package tj.iskandar.guidebook.navigation

import androidx.compose.animation.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.iskandar.guidebook.presentation.MainScreen
import tj.iskandar.guidebook.presentation.WebViewScreen


sealed class NavigationGuide(val route: String){

    object MainScreen: Screen("guide_screen")
    object WebViewScreen : Screen("webview/{url}") {
        fun withArgs(url: String): String = "webview/$url"
    }



    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.omdb(
    navController: NavHostController,
    restartActivity: () -> Unit,
) {

    navigation(
        route = Screen.MainScreen.route,
        startDestination = NavigationGuide.MainScreen.route,
    ) {


        composable(route = NavigationGuide.MainScreen.route)
        {
            MainScreen(navController)
        }
        composable(
            route = NavigationGuide.WebViewScreen.route

        ) { entry ->
            val url = entry.arguments?.getString("url")
            WebViewScreen(
                navController = navController,
                url = url
            )
        }


    }
}