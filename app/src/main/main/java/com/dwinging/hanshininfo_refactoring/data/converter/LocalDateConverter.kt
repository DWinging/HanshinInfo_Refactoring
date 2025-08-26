package com.dwinging.hanshininfo_refactoring.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    fun fromDateType(value: String): LocalDate = value.let { LocalDate.parse(it, formatter) }

    @TypeConverter
    fun toDateType(date: LocalDate): String = date.format(formatter)
}