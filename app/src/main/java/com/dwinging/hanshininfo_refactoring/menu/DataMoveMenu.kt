package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dwinging.hanshininfo_refactoring.R

enum class MoveMenuList {
    // sub
    HOMEPAGE,
    HSGUIDE,
    LMS,
    EROOM,
    ATTEND,
    SUGANG
}

sealed class MoveMenuItem{
    abstract val type: MoveMenuList
    abstract val title: String

    data class LinkMenu(
        override val type: MoveMenuList,
        override val title: String,
        val url: String
    ): MoveMenuItem()

    data class AppMenu(
        override val type: MoveMenuList,
        override val title: String,
        val packageName: String
    ): MoveMenuItem()
}

@Composable
fun getMoveMenuItems(): List<MoveMenuItem> {
    return listOf(
        MoveMenuItem.LinkMenu(
            type = MoveMenuList.HOMEPAGE,
            title = stringResource(R.string.homepage),
            url = "https://hs.ac.kr/sites/kor/index.do"
        ),
        MoveMenuItem.LinkMenu(
            type = MoveMenuList.HSGUIDE,
            title = stringResource(R.string.hs_guide),
            url = "https://www.hs.ac.kr/sites/hsguide/index.do"
        ),
        MoveMenuItem.LinkMenu(
            type = MoveMenuList.LMS,
            title = stringResource(R.string.lms),
            url = "https://lms.hs.ac.kr/main/MainView.dunet#main"
        ),
        MoveMenuItem.LinkMenu(
            type = MoveMenuList.EROOM,
            title = stringResource(R.string.e_room),
            url = "https://result.hs.ac.kr/ptfol/imng/sbjtMngt/findLssnList.do"
        ),
        MoveMenuItem.AppMenu(
            type = MoveMenuList.ATTEND,
            title = stringResource(R.string.attend),
            packageName = "com.libeka.attendance.ucheckplusstud_hanshin"
        ),
        MoveMenuItem.AppMenu(
            type = MoveMenuList.SUGANG,
            title = stringResource(R.string.sugang),
            packageName = "kr.co.swit.hsuv"
        )
    )
}