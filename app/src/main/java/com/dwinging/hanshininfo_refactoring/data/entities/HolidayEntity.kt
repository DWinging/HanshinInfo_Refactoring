package com.dwinging.hanshininfo_refactoring.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName="Holiday")
data class HolidayEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name="dateName")
    val name: String,

    @ColumnInfo(name="locdate")
    val date: LocalDate
)

data class Holidays(
    @ColumnInfo(name = "locdate")
    val date: LocalDate
)