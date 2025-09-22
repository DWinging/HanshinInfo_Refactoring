package com.dwinging.hanshininfo_refactoring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwinging.hanshininfo_refactoring.data.entities.HolidayEntity
import com.dwinging.hanshininfo_refactoring.data.entities.Holidays
import java.time.LocalDate

@Dao
interface HolidayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolidayInfo(holiday: List<HolidayEntity>)

    @Query("""DELETE FROM Holiday""")
    suspend fun clearAllHolidayInfo()

    @Query("""SELECT locdate 
        FROM Holiday 
        WHERE locdate BETWEEN :startOfMonth AND :endOfMonth
        ORDER BY locdate ASC"""
    )
    suspend fun findHoliday(startOfMonth:LocalDate, endOfMonth: LocalDate): List<Holidays>

    @Query("SELECT * FROM Holiday")
    suspend fun getAllHolidays(): List<Holidays>
}