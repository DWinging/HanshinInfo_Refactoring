package com.dwinging.hanshininfo_refactoring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwinging.hanshininfo_refactoring.data.entities.ScheduleEntity
import com.dwinging.hanshininfo_refactoring.view.schedule.EventList

@Dao
interface ScheduleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: List<ScheduleEntity>)

    @Query("""DELETE FROM Schedule""")
    suspend fun clearAllSchedule()

    @Query("""SELECT event, startDate, endDate 
        FROM Schedule 
        WHERE NOT (endDate < :startOfMonth OR startDate > :endOfMonth)
        ORDER BY startDate ASC
        """)
    suspend fun findSchedule(startOfMonth:String, endOfMonth: String): List<EventList>
}