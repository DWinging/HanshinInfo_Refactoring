package com.dwinging.hanshininfo_refactoring.view.schedule

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.data.db.AppDatabase
import java.time.LocalDate
import java.time.YearMonth

data class EventList(
    val event: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)

suspend fun getEventList(date: LocalDate, context: Context): List<EventList> {
    val scheduleDAO = AppDatabase.getDatabase(context).scheduleDao()
    val yearMonth = YearMonth.from(date)
    val startOfMonth = yearMonth.atDay(1).toString()
    val endOfMonth = yearMonth.atEndOfMonth().toString()
    return scheduleDAO.findSchedule(startOfMonth, endOfMonth)
}

fun inputEvent(eventList: List<EventList>, date: LocalDate): Array<ArrayList<String>> {
    val yearMonth = YearMonth.from(date)
    val daysInMonth = yearMonth.lengthOfMonth()
    val dayArray = Array(daysInMonth) { ArrayList<String>() }

    eventList.forEach { event ->
        val start = event.startDate
        val end = event.endDate

        val startDay = start.dayOfMonth.coerceAtLeast(1)
        val endDay = end.dayOfMonth.coerceAtMost(daysInMonth)

        for(day in startDay .. endDay) {
            dayArray[day-1].add(event.event)
        }
    }
    return dayArray
}

@Composable
fun getDayOfWeekColor(dayOfWeek: Int): Color =
    when(dayOfWeek) {
        0 -> colorResource(id = R.color.sunday)
        6 -> colorResource(id = R.color.saturday)
        else -> colorResource(id = R.color.weekdays)
    }