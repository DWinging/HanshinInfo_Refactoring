package com.dwinging.hanshininfo_refactoring.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApiService {
    @GET("B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
    suspend fun getHolidays(
        @Query("serviceKey") apiKey: String?,
        @Query("solYear") year: Int,
        @Query("_type") type: String = "json", // 항상 JSON으로 요청
        @Query("numOfRows") rows: Int = 30
    ): HolidayResponse
}