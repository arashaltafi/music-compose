package ir.arash.altafi.musiccompose.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ir.arash.altafi.musiccompose.ui.component.ImageScreen
import ir.arash.altafi.musiccompose.ui.presentation.celebrity.CelebrityScreen
import ir.arash.altafi.musiccompose.ui.presentation.home.HomeScreen
import ir.arash.altafi.musiccompose.ui.presentation.main.MainScreen
import ir.arash.altafi.musiccompose.ui.presentation.main.MainScreen2
import ir.arash.altafi.musiccompose.ui.presentation.paging.PagingScreen
import ir.arash.altafi.musiccompose.ui.presentation.testDetail.TestDetail
import ir.arash.altafi.musiccompose.ui.presentation.testList.TestList
import ir.arash.altafi.musiccompose.ui.presentation.testPagingList.TestPagingList
import ir.arash.altafi.musiccompose.ui.presentation.user.UserScreen
import ir.arash.altafi.musiccompose.ui.theme.MusicComposeTheme

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    MusicComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Home,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Route.Main> {
                    MainScreen(navController)
                }
                composable<Route.Main2> {
                    MainScreen2(navController)
                }
                composable<Route.User> {
                    UserScreen(navController)
                }
                composable<Route.Celebrity> {
                    CelebrityScreen(navController)
                }
                composable<Route.Paging> {
                    PagingScreen(navController)
                }
                composable<Route.ImageScreen> { backStackEntry: NavBackStackEntry ->
                    val args = backStackEntry.toRoute<Route.ImageScreen>()
                    ImageScreen(navController, args.title, args.imageUrl)
                }
                composable<Route.TestList> {
                    TestList(navController)
                }
                composable<Route.TestPagingList> {
                    TestPagingList(navController)
                }
                composable<Route.TestDetail> { backStackEntry: NavBackStackEntry ->
                    val args = backStackEntry.toRoute<Route.TestDetail>()
                    TestDetail(args.userId, navController)
                }
                composable<Route.Home> {
                    HomeScreen(navController)
                }
            }
        }
    }
}