package com.dwinging.hanshininfo_refactoring.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Schedule")
data class ScheduleEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "event")
    val event: String,

    @ColumnInfo(name="startDate")
    val startDate: LocalDate,

    @ColumnInfo(name="endDate")
    val endDate: LocalDate
)