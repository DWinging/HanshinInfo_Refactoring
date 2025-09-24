package com.dwinging.hanshininfo_refactoring.menu

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.ui.theme.Purple200
import com.dwinging.hanshininfo_refactoring.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    // --- 상태(State) ---
    currentPage: MainMenuList,
    isSearchMode: Boolean,
    searchQuery: String,

    // --- 동작(event) ---
    onQueryChange: (String) -> Unit,
    onSearchConfirm: () -> Unit,
    onOpenSearchClick: () -> Unit,
    onCloseSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onMenuClick: () -> Unit
) {

    val title = getPageName(currentPage)
    val keyboardController = LocalSoftwareKeyboardController.current

    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = Purple200,
            titleContentColor = WhiteColor
        ),
        title = {
            if (isSearchMode) {
                TextField(
                    value = searchQuery,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 14.sp),
                    placeholder = { Text("검색...") },
                    onValueChange = { newText ->
                        onQueryChange(newText)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchConfirm()
                        keyboardController?.hide()
                    })
                )
            } else {
                Text(
                    text = title,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            if(isSearchMode) {
                SetIconButton(Icons.AutoMirrored.Filled.ArrowBack, "Back", onClick = {
                    onClearClick()
                    onCloseSearchClick()
                })
            }
            else {
                SetIconButton(Icons.Filled.Menu, "Menu", onClick = onMenuClick)
            }
        },
        actions = {
            if(currentPage == MainMenuList.NUMBER_EXT || currentPage == MainMenuList.NUMBER_PRO) {
                if(isSearchMode) {
                    SetIconButton(Icons.Filled.Clear, "clear", onClick = onClearClick)
                }
                else {
                    SetIconButton(Icons.Filled.Search, "Search", onClick = onOpenSearchClick)
                }
            }
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
fun SetIconButton(
    icons: ImageVector,
    content: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icons,
            contentDescription = content,
            tint = WhiteColor
        )
    }
}