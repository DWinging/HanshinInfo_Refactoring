package com.dwinging.hanshininfo_refactoring.mainUI

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.menu.MainMenuList
import com.dwinging.hanshininfo_refactoring.view.campusMap.CampusMapView
import com.dwinging.hanshininfo_refactoring.view.number.NumberView
import com.dwinging.hanshininfo_refactoring.view.schedule.ScheduleView

@Composable
fun AppNavHost(currentPage: MainMenuList, drawerState: DrawerState) {
    val navController = rememberNavController()

    LaunchedEffect(currentPage) {
        when (currentPage) {
            MainMenuList.CAMPUS_MAP -> navController.navigate("${currentPage}_route")
            MainMenuList.SCHEDULE -> navController.navigate("${currentPage}_route")
            MainMenuList.NUMBER_EXT -> navController.navigate("${currentPage}_route")
            MainMenuList.NUMBER_PRO -> navController.navigate("${currentPage}_route")

            MainMenuList.NUMBER -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = "campus_map_route" // 시작 화면 라우트
    ) {
        composable("campus_map_route") {
            CampusMapView()
            LaunchedEffect(currentPage) {
                drawerState.close()
            }
        }
        composable("schedule_route") {
            ScheduleView()
            LaunchedEffect(currentPage) {
                drawerState.close()
            }
        }
        composable("number_ext_route") {
            NumberView(NumberType.NUMBER_EXT)
            LaunchedEffect(currentPage) {
                drawerState.close()
            }
        }
        composable("number_pro_route") {
            NumberView(NumberType.NUMBER_PRO)
            LaunchedEffect(currentPage) {
                drawerState.close()
            }
        }
    }
}
