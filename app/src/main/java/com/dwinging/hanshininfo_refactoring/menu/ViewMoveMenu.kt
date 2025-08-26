package com.dwinging.hanshininfo_refactoring.menu

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.core.net.toUri
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles

@Composable
fun MoveMenu() {
    val list = getMoveMenuItems()
    Column {
        list.forEach { item -> MoveMenuView(item) }
    }
}

@Composable
fun MoveMenuView(item: MoveMenuItem) {
    val style = ComponentTextStyles.MainMenu
    val paddingValue = ComponentTextStyles.MainMenuPadding
    when(item) {
        is MoveMenuItem.LinkMenu -> LinkMenuView(item, style, paddingValue)
        is MoveMenuItem.AppMenu -> AppMenuView(item, style, paddingValue)
    }
}

@Composable
fun LinkMenuView(
    item: MoveMenuItem.LinkMenu,
    style: TextStyle,
    modifier: Modifier
) {
    val context = LocalContext.current
    Text(
        text = item.title,
        style = style,
        modifier = modifier
            .then(Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
                    context.startActivity(intent)
                }
            )
    )
}

@Composable
fun AppMenuView(
    item: MoveMenuItem.AppMenu,
    style: TextStyle,
    modifier: Modifier
) {
    val context = LocalContext.current
    Text(
        text = item.title,
        style = style,
        modifier = modifier
            .then(Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    val intent = context.packageManager.getLaunchIntentForPackage(item.packageName)
                    if (intent != null) {
                        context.startActivity(intent)
                    } else {
                        val url = "market://details?id=${item.packageName}"
                        val marketIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(marketIntent)
                    }
                }
            )
    )
}