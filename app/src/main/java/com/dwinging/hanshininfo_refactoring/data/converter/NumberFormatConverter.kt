package com.dwinging.hanshininfo_refactoring.data.converter

import androidx.room.TypeConverter
import com.dwinging.hanshininfo_refactoring.data.entities.PhoneNumber

class NumberFormatConverter {
    @TypeConverter
    fun fromNumberFormat(number: PhoneNumber): String =
        number.value.replace("-", "")

    @TypeConverter
    fun toNumberFormat(number: String): PhoneNumber {
        val formattingValue = formatNumberFunction(number)
        return PhoneNumber(formattingValue)
    }

    private fun formatNumberFunction(number: String): String =
        when {
            number.startsWith("02") && number.length >= 9 ->
                formatWithPrefixLength(number, 2)
            number.startsWith("0") && number.length >= 10 ->
                formatWithPrefixLength(number, 3)
            number.startsWith("1") && number.length == 8 ->
                "${number.substring(0, 4)}-${number.substring(4, 8)}"
            else -> number
        }

    private fun formatWithPrefixLength(number: String, prefixLength: Int): String {
        val prefix = number.substring(0, prefixLength)
        val rest = number.substring(prefixLength)
        val midPoint = rest.length / 2

        val middle = rest.substring(0, midPoint)
        val last = rest.substring(midPoint)

        return "$prefix-$middle-$last"
    }
}