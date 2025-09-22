package com.dwinging.hanshininfo_refactoring.mainUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwinging.hanshininfo_refactoring.menu.DrawerContent
import com.dwinging.hanshininfo_refactoring.menu.MainTopAppBar
import com.dwinging.hanshininfo_refactoring.menu.MenuViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MenuViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentPage by viewModel.selectedMenu.collectAsState()

    ModalNavigationDrawer(
        // 메뉴창
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        Scaffold(
            // 상단바
            topBar = { MainTopAppBar(
                currentPage,
                onMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )}
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                AppNavHost(currentPage, drawerState)
            }
        }
    }
}


@Composable
@Preview
fun MyAppPreview () {
    MainScreen()
}