package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dwinging.hanshininfo_refactoring.R

enum class MainMenuList {
    // main
    CAMPUS_MAP,
    SCHEDULE,
    NUMBER,
    NUMBER_EXT,
    NUMBER_PRO,
}

sealed class MenuItem{
    abstract val type: MainMenuList
    abstract val title: String

    data class SimpleMenu(
        override val type: MainMenuList,
        override val title: String
    ): MenuItem()

    data class GroupMenu(
        override val type: MainMenuList,
        override val title: String,
        val subMenus: List<MenuItem>
    ): MenuItem()
}

@Composable
fun getMainMenuItems(): List<MenuItem> {
    return listOf(
        MenuItem.SimpleMenu(
            type = MainMenuList.CAMPUS_MAP,
            title = stringResource(R.string.campus_map)
        ),
        MenuItem.SimpleMenu(
            type = MainMenuList.SCHEDULE,
            title = stringResource(R.string.schedule)
        ),
        MenuItem.GroupMenu(
            type = MainMenuList.NUMBER,
            title = stringResource(R.string.number),
            subMenus = listOf(
                MenuItem.SimpleMenu(
                    type = MainMenuList.NUMBER_EXT,
                    title = stringResource(R.string.number_ext)
                ),
                MenuItem.SimpleMenu(
                    type = MainMenuList.NUMBER_PRO,
                    title = stringResource(R.string.number_pro)
                )
            )
        )
    )
}
