package com.dwinging.hanshininfo_refactoring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwinging.hanshininfo_refactoring.data.entities.NumberDetail
import com.dwinging.hanshininfo_refactoring.data.entities.NumberEntity
import com.dwinging.hanshininfo_refactoring.data.entities.NumberList
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType

@Dao
interface NumberDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: List<NumberEntity>)

    @Query("""DELETE FROM Number""")
    suspend fun clearAllNumber()

    @Query("""SELECT id, affiliation, name, number FROM Number WHERE type = :type""")
    suspend fun findAllNumber(type: NumberType): List<NumberList>

    @Query("""SELECT affiliation, department, name, number, room FROM Number WHERE id = :id AND type = :type""")
    suspend fun findNumberDetail(id: Int, type:NumberType): NumberDetail?
}