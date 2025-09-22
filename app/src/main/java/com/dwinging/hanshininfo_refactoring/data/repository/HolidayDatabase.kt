package com.dwinging.hanshininfo_refactoring.data.repository

import android.content.Context
import android.util.Log
import com.dwinging.hanshininfo_refactoring.data.api.ApiClient
import com.dwinging.hanshininfo_refactoring.data.dao.HolidayDAO
import com.dwinging.hanshininfo_refactoring.data.entities.HolidayEntity
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun insertHolidayData(
    context: Context,
    year: Int,
    holidayDAO: HolidayDAO
) = withContext(Dispatchers.IO) {
        try {
            val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            val apiKey = applicationInfo.metaData.getString("HOLIDAY_API_KEY")

            val response = ApiClient.holidayApi.getHolidays(
                apiKey = apiKey,
                year = year
            )

            Log.d("HolidayCheck", "API 데이터: ${response.response.body.items.item}")

            val holidayEntities = response.response.body.items.item
                .filter { it.isHoliday == "Y" }
                .map {
                    HolidayEntity(
                        name = it.dateName,
                        date = LocalDate.parse(
                            it.locdate.toString(),
                            DateTimeFormatter.BASIC_ISO_DATE
                        )
                    )
                }

            holidayDAO.insertHolidayInfo(holidayEntities)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.d("ApiHostFail", "Fail")
        }
    }