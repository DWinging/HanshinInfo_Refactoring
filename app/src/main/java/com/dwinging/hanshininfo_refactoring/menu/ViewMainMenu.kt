package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles

@Composable
fun MainMenu(
    onMenuClick: (MainMenuList) -> Unit = {},
    viewModel: MenuViewModel
) {
    val list = getMainMenuItems()
    Column {
        list.forEach { item -> MenuItemView(item, onMenuClick, viewModel) }
    }
}

@Composable
fun MenuItemView(
    item: MenuItem,
    onMenuClick: (MainMenuList) -> Unit,
    viewModel: MenuViewModel,
    style: TextStyle = ComponentTextStyles.MainMenu,
    padding: Modifier = ComponentTextStyles.MainMenuPadding
) {
    when(item) {
        is MenuItem.SimpleMenu -> {
            SimpleMenuItemView(item, onMenuClick, viewModel, style, padding)
        }
        is MenuItem.GroupMenu -> {
            val expandedMap = viewModel.expandedMap
            val isExpanded = expandedMap[item.type] == true
            GroupMenuItemView(item, onMenuClick, isExpanded, viewModel)
        }
    }
}

@Composable
fun GroupMenuItemView(
    item: MenuItem.GroupMenu,
    onMenuClick: (MainMenuList) -> Unit,
    isExpanded: Boolean,
    viewModel: MenuViewModel,
    style: TextStyle = ComponentTextStyles.MainMenu,
    modifier: Modifier = ComponentTextStyles.MainMenuPadding
) {
    Column {
        Text(
            text = item.title,
            style = style,
            modifier = modifier
                .then(Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { viewModel.toggleExpand(item.type)}
            )
        )
        if (isExpanded) {
            val style = ComponentTextStyles.SubMenu
            val padding = ComponentTextStyles.subMenuPadding
            item.subMenus.forEach { sub -> MenuItemView(sub, onMenuClick, viewModel, style, padding) }
        }
    }
}

@Composable
fun SimpleMenuItemView(
    item: MenuItem.SimpleMenu,
    onMenuClick: (MainMenuList) -> Unit,
    viewModel: MenuViewModel,
    style: TextStyle = ComponentTextStyles.MainMenu,
    modifier: Modifier = ComponentTextStyles.MainMenuPadding
) {
    Text(
        text = item.title,
        style = style,
        modifier = modifier
            .then( Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onMenuClick(item.type)
                viewModel.delayedCollapse()
            }
        )
    )
}

