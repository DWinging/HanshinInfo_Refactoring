package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwinging.hanshininfo_refactoring.R

@Composable
fun DrawerContent(viewModel: MenuViewModel = viewModel()) {
    Surface(
        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp),
        color = Color.White
    ){
        LazyColumn {
            item { DrawImage() }

            // MainMenu
            item { MainMenu(onMenuClick = { menu -> viewModel.mainMenuSelected(menu) }, viewModel) }

            // 구분선(MainMenu / SubMenu)
            item { DrawSpacer() }

            // SubMenu - Url, App
            item { MoveMenu() }
        }
    }
}

@Composable
fun DrawImage() {
    Image(
        painter = painterResource(id = R.drawable.mascot_image),
        contentDescription = "MenuTopImage",
        modifier = Modifier.width(300.dp)
            .padding(bottom = 10.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DrawSpacer() {
    Spacer(modifier = Modifier
        .padding(top = 10.dp, bottom = 10.dp)
        .height(1.dp)
        .fillMaxWidth()
        .background(Color.Black)
    )
}

@Preview
@Composable
fun DrawerContentPreview() {
    DrawerContent()
}