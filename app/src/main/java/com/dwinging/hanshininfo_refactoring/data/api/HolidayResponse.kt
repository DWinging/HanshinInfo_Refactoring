package com.dwinging.hanshininfo_refactoring.data.api

data class HolidayResponse (
    val response: ResponseWrapper
)

data class ResponseWrapper(
    val body: Body
)

data class Body(
    val items: Items
)

data class Items(
    val item: List<HolidayItem>
)

data class HolidayItem(
    val locdate: Long,
    val dateName: String,
    val isHoliday: String
)