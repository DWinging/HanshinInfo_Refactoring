package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.ui.theme.Purple200
import com.dwinging.hanshininfo_refactoring.ui.theme.WhiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(currentPage: MainMenuList, onMenuClick: () -> Unit) {

    val title = getPageName(currentPage)

    TopAppBar(
        colors = topAppBarColors(
            containerColor = Purple200,
            titleContentColor = WhiteColor
        ),
        navigationIcon = {
            HamburgerButton(onMenuClick = onMenuClick)
        },
        title = {
            Text(title)
        }
    )
}

@Composable
fun getPageName(currentPage: MainMenuList): String =
    when(currentPage) {
        MainMenuList.CAMPUS_MAP -> stringResource(R.string.campus_map)
        MainMenuList.SCHEDULE -> stringResource(R.string.schedule)
        MainMenuList.NUMBER -> stringResource(R.string.number)
        MainMenuList.NUMBER_EXT -> stringResource(R.string.number_ext)
        MainMenuList.NUMBER_PRO -> stringResource(R.string.number_pro)
    }

@Composable
fun HamburgerButton(onMenuClick: () -> Unit) {
    IconButton(onClick = onMenuClick) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            tint = WhiteColor
        )
    }
}