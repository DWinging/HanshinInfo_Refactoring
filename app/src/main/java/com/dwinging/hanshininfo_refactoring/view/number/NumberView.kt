package com.dwinging.hanshininfo_refactoring.view.number

import android.app.Application
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwinging.hanshininfo_refactoring.data.entities.NumberList
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles
import com.dwinging.hanshininfo_refactoring.view.components.BottomSheetView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberView(type: NumberType, viewModel: NumberViewModel = viewModel()) {
    val context = LocalContext.current.applicationContext as Application
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showSheet by viewModel.sheetData
    val numberList by viewModel.numberList
    val query by viewModel.confirmQuery

    LaunchedEffect(type) {
        viewModel.resetConfirmQuery()
    }

    LaunchedEffect(type, query) {
        viewModel.loadNumbers(type, context)
    }

    Column {
        Category(type)
        MainContent(
            numberList = numberList,
            onClickItem = { id ->
                viewModel.showSheet(id, type, context)
                scope.launch { sheetState.show() }
            }
        )
    }

    val selectedDetail by viewModel.selectedDetail
    if(showSheet.state) {
        BottomSheetView(
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide().also {
                        viewModel.onDismissSheet()
                    }
                }
            }
        ){
            NumberDetail(numberDetail = selectedDetail, type = type)
        }
    }
}

@Composable
fun Category(type: NumberType) {
    Row(
        modifier = ComponentTextStyles.ContentTitleStyle
    ) {
        RowText("부서", 1.25f)
        if(type == NumberType.NUMBER_PRO) RowText("이름", 2f)
        else RowText("업무명", 2f)
    }
}

@Composable
private fun MainContent(
    numberList: List<NumberList>,
    onClickItem: (Int) -> Unit
) {
    LazyColumn {
        items(numberList) { item ->
            Column(
                modifier = ComponentTextStyles.ContentRowStyle
                    .then(Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClickItem(item.id)
                    })
            ) {
                Row {
                    RowText(item.affiliation, 1.25f)

                    if (item.name.isNotEmpty()) RowText(item.name, 2f)
                    else RowText(item.department, 2f)
                }
                Row {
                    RowText("전화번호 :", 1.25f)
                    RowText(item.number.value, 2f)
                }
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
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = ComponentTextStyles.TextPadding
            .then(
                Modifier.weight(weight)
                    .wrapContentWidth(Alignment.Start)
            )
    )
}