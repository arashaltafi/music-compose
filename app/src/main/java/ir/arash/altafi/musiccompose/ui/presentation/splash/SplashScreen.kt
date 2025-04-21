package ir.arash.altafi.musiccompose.ui.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.arash.altafi.musiccompose.R
import com.idapgroup.snowfall.snowfall
import ir.arash.altafi.musiccompose.ui.component.LottieComponent
import ir.arash.altafi.musiccompose.ui.navigation.Route
import ir.arash.altafi.musiccompose.ui.theme.CustomFont
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val splashViewModel: SplashViewModel = hiltViewModel()
    val token by splashViewModel.cachedToken.observeAsState()

    val versionName =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1"

    LaunchedEffect(Unit) {
        splashViewModel.getToken()
    }

    LaunchedEffect(Unit) {
        delay(2000)
//        navController.navigate(Route.Home)
        navController.navigate(
            if (token != null && token != "") Route.Home else Route.Login
        ) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.blue_900))
            .snowfall(
                colors = listOf(Color.White),
                density = 0.01,
                alpha = 0.2f
            )
            .padding(horizontal = 32.dp, vertical = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.app_name),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont,
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LottieComponent(
                    size = DpSize(width = 200.dp, height = 200.dp),
                    loop = true,
                    lottieFile = R.raw.chat_3
                )
            }

            Text(
                text = versionName,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont
            )
        }
    }
}