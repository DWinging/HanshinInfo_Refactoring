package com.dwinging.hanshininfo_refactoring.data.repository

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.data.dao.ScheduleDAO
import com.dwinging.hanshininfo_refactoring.data.entities.ScheduleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun insertScheduleData(context: Context, scheduleDAO: ScheduleDAO) =
    withContext(Dispatchers.IO) {
        try {
            val url = context.getString(R.string.number_schedule_url)
            val doc = Jsoup.connect(url).get()
            val ulElements = doc.select("#timeTableList > ul")

            val regex = Regex("""\d{2}""")
            val year = LocalDate.now().year
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val scheduleDataList = mutableListOf<ScheduleEntity>()
            for (li in ulElements) {
                val dtList = li.select("dt > span")
                val ddList = li.select("dd > a")

                for (i in dtList.indices) {
                    scheduleDataList.add(
                        parseScheduleItem(i, dtList, ddList, year, regex, formatter)
                    )
                }
            }

            scheduleDAO.clearAllSchedule()
            scheduleDAO.insertSchedule(scheduleDataList)
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                AlertDialog.Builder(context)
                    .setTitle("네트워크 오류")
                    .setMessage("네트워크 연결이 불안정합니다.\n" +
                            "연결 상태를 확인한 후 다시 시도해주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인") { _, _ ->
                        // 앱 종료
                        (context as Activity).finish()
                    }
                    .show()
            }
        }
    }

fun parseScheduleItem(
    idx: Int,
    dtList: Elements,
    ddList: Elements,
    year: Int,
    regex: Regex,
    formatter: DateTimeFormatter
): ScheduleEntity {
    val event = ddList.getOrNull(idx)?.text() ?: ""

    val date = regex.findAll(dtList[idx].text()).map { it.value }.toList()
    val startDate = LocalDate.parse("${year}-${date[0].padStart(2, '0')}-${date[1].padStart(2, '0')}", formatter)
    val endDate = if (date.size >= 4) {
        LocalDate.parse("${year}-${date[2].padStart(2, '0')}-${date[3].padStart(2, '0')}", formatter)
    } else {
        startDate
    }

    return ScheduleEntity(
        event = event,
        startDate = startDate,
        endDate = endDate
    )
}