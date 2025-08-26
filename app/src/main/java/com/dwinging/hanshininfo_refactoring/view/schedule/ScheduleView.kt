package com.dwinging.hanshininfo_refactoring.view.schedule

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles
import com.dwinging.hanshininfo_refactoring.ui.theme.Purple700
import java.time.LocalDate

@Composable
fun ScheduleView() {
    val context = LocalContext.current.applicationContext as Application
    val date = remember { mutableStateOf(LocalDate.now()) }
    val eventList = remember { mutableStateOf<List<EventList>>(emptyList()) }

    LaunchedEffect(date.value) {
        eventList.value = getEventList(date.value, context)
    }

    val dayList = inputEvent(eventList.value, date.value)
    Column {
        Row (
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = { date.value = date.value.minusMonths(1) },
                enabled = date.value.monthValue > 1
            ) { Text("◀") }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "${date.value.monthValue}월",
                style = ComponentTextStyles.MonthText,
                color = Purple700
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { date.value = date.value.plusMonths(1) },
                enabled = date.value.monthValue < 12
            ) { Text("▶") }
        }
        LazyColumn(Modifier.fillMaxWidth()) {
            items(dayList.size) { day ->
                Row (
                    ComponentTextStyles.ContentRowStyle
                ){
                    TextRow( "${day + 1}일", "${dayList[day].trimEnd()}")
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun TextRow(label:String, value:String) {
    Row {
        Text(
            label,
            style = ComponentTextStyles.LabelText,
            modifier = ComponentTextStyles.LabelPadding
        )
        Text(
            text = value,
            style = ComponentTextStyles.ValueText,
            modifier = ComponentTextStyles.ValuePadding
        )
    }
}