package com.dwinging.hanshininfo_refactoring.view.number

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.dwinging.hanshininfo_refactoring.data.entities.NumberDetail
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.ui.theme.BlackColor
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles
import com.dwinging.hanshininfo_refactoring.ui.theme.Purple200

@Composable
fun NumberDetail(id: Int, type: NumberType) {
    val context = LocalContext.current
    val numberDetail = remember { mutableStateOf<NumberDetail?>(null) }

    LaunchedEffect(id) {
        numberDetail.value = getNumberDetail(id, type, context)
    }

    Column {
        Title()
        Content(numberDetail)
    }
}

@Composable
private fun Title() {
    Text(
        text = "상세 정보",
        Modifier.padding(start = 30.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Content(numberDetail: MutableState<NumberDetail?>) {
    val context = LocalContext.current

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .border(
            width = 0.5.dp,
            color = BlackColor,
            shape = RoundedCornerShape(16.dp)
        ),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 5.dp
    ) {
        Column {
            TextRow(label = "부서", value = numberDetail.value?.affiliation)
            TextRow(label = "업무명", value = numberDetail.value?.department)
            val name = numberDetail.value?.name ?: ""
            if(name.isNotEmpty()) TextRow(label = "이름", value = name)
            TextRow(
                label = "전화번호",
                value = numberDetail.value?.number,
                onValueClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${numberDetail.value?.number}".toUri()
                    }
                    context.startActivity(intent)
                })
            TextRow(label = "실번호", value = numberDetail.value?.room)
        }
    }
}

@Composable
private fun TextRow(
    label: String,
    value: String?,
    onValueClick: (() -> Unit)? = null
) {
    Row {
        Text(
            label,
            style = ComponentTextStyles.LabelText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
                .background(Purple200)
                .then(ComponentTextStyles.LabelPadding)
        )
        Text(
            text = value ?: "",
            style = ComponentTextStyles.ValueText.copy(
                color = if (label == "전화번호") Color(0xFF1E88E5) else ComponentTextStyles.ValueText.color,
                textDecoration = if (label == "전화번호") TextDecoration.Underline else null
            ),
            textAlign = TextAlign.Start,
            modifier = if (onValueClick != null) {
                            Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onValueClick() }
                        } else { Modifier }
                .weight(2f)
                .then (ComponentTextStyles.LabelPadding)
        )
    }
}