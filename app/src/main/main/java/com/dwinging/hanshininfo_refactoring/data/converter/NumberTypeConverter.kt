package com.dwinging.hanshininfo_refactoring.data.converter

import androidx.room.TypeConverter
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType

class NumberTypeConverter {
    @TypeConverter
    fun fromNumberType(value: NumberType): String = value.name

    @TypeConverter
    fun toNumberType(value: String): NumberType = NumberType.valueOf(value)
}