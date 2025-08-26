package com.dwinging.hanshininfo_refactoring.splash

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.data.db.CheckBuildingData
import com.dwinging.hanshininfo_refactoring.data.db.CheckLastUpdateNumber
import com.dwinging.hanshininfo_refactoring.data.db.CheckLastUpdateSchedule
import kotlinx.coroutines.delay
import com.dwinging.hanshininfo_refactoring.data.prefs.PrefsManager
import com.dwinging.hanshininfo_refactoring.ui.theme.Purple700

@Composable
fun SplashScreen(navController: NavHostController) {

    val scale = remember { Animatable(0f) }
    val context = LocalContext.current
    PrefsManager.init(context)
    CheckDatabase(context)

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000, easing = EaseOutBounce)
        )
        delay(1500)
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple700),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_splash),
            contentDescription = "SplashImage",
            modifier = Modifier.width(270.dp)
                .height(270.dp)
                .padding(bottom = 10.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CheckDatabase(context: Context) {
    CheckBuildingData(context)
    CheckLastUpdateSchedule(context)
    CheckLastUpdateNumber(context)
}

@Composable
@Preview
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(navController)
}