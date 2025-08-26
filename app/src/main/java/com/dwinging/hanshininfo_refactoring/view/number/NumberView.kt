package com.dwinging.hanshininfo_refactoring.view.number

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dwinging.hanshininfo_refactoring.data.entities.NumberList
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles
import com.dwinging.hanshininfo_refactoring.view.components.BottomSheetView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberView(type: NumberType) {
    val context = LocalContext.current.applicationContext as Application
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(NumberDataSheet(false, -1)) }
    val numberList = remember { mutableStateOf<List<NumberList>>(emptyList()) }
    LaunchedEffect(type) {
        numberList.value = getAllNumbers(type, context)
    }


    Column {
        Category(type)
        MainContent(
            numberList = numberList,
            onClickItem = { id ->
                showSheet = NumberDataSheet(true, id)
                scope.launch { sheetState.show() }
            }
        )
    }

    if(showSheet.state) {
        BottomSheetView(
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                    showSheet = showSheet.copy(state = false, id = -1)
                }
            }
        ){
            NumberDetail(showSheet.id, type)
        }
    }
}

@Composable
fun Category(type: NumberType) {
    Row(
        modifier = ComponentTextStyles.ContentTitleStyle
    ) {
        RowText("부서", 1f)
        if(type == NumberType.NUMBER_PRO) RowText("이름", 1f)
        RowText("전화번호", 2f)
    }
}

@Composable
private fun MainContent(
    numberList: MutableState<List<NumberList>>,
    onClickItem: (Int) -> Unit
) {
    LazyColumn {
        items(numberList.value) { item ->
            Row(
                modifier = ComponentTextStyles.ContentRowStyle
                    .then(Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClickItem(item.id)
                    })
            ) {
                RowText(item.affiliation, 1f)
                if (item.name.isNotEmpty()) { RowText(item.name, 1f) }
                RowText(item.number, 2f)
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RowScope.RowText(
    text: String,
    weight: Float = 1F
) {
    Text(
        text,
        style = ComponentTextStyles.ValueText,
        modifier = ComponentTextStyles.TextPadding
            .then(
                Modifier.weight(weight)
                    .wrapContentWidth(Alignment.Start)
            )
    )
}