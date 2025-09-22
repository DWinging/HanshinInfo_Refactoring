package com.dwinging.hanshininfo_refactoring.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val holidayApi: HolidayApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HolidayApiService::class.java)
    }
}