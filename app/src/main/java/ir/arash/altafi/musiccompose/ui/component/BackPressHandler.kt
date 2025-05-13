package ir.arash.altafi.musiccompose.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ir.arash.altafi.musiccompose.ui.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BackPressHandler(
    navController: NavController,
    onNavigationItemSelected: (Int) -> Unit
) {
    var doubleBackToExitPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = (context as? Activity)
    val coroutineScope = rememberCoroutineScope()
    val packageName = context.packageName

    // Use BackHandler to intercept the system back press
    BackHandler {
        if (navController.previousBackStackEntry != null) {
            // Pop the backstack if there is a previous route
            navController.popBackStack()
            onNavigationItemSelected(0)
            onNavigationItemSelected(
                when (navController.currentDestination?.route) {
                    packageName + Route.Home.route -> 0
                    packageName + Route.Music.route -> 1
                    packageName + Route.MusicVideo.route -> 2
                    packageName + Route.Profile.route -> 3
                    else -> 0
                }
            )
        } else {
            // Handle double back press to exit the app
            if (doubleBackToExitPressedOnce) {
                // Exit the app if back is pressed twice within 5 seconds
                activity?.finish()
            } else {
                // Show the toast message and start a 5-second timer
                doubleBackToExitPressedOnce = true
                Toast.makeText(
                    context,
                    "برای خروج یک بار دیگر دکمه برگشت را بزنید",
                    Toast.LENGTH_SHORT
                ).show()

                // Reset the flag after 5 seconds using coroutine
                coroutineScope.launch {
                    delay(5000)  // 5-second delay
                    doubleBackToExitPressedOnce = false
                }
            }
        }
    }
}
