package com.dwinging.hanshininfo_refactoring.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.dwinging.hanshininfo_refactoring.data.entities.AmenityEntity
import com.dwinging.hanshininfo_refactoring.data.entities.BuildingEntity

data class BuildingInfo(
    @ColumnInfo(name = "building_num")
    val buildingNum: Int,

    @ColumnInfo(name = "building_name")
    val buildingName: String
)

data class BuildingWithAmenities(
    @Embedded val building: BuildingInfo,
    @Relation(
        parentColumn = "building_num",
        entityColumn = "building_num"
    )
    val amenities: List<AmenityEntity>
)

@Dao
interface BuildingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildingEntity(building: List<BuildingEntity>)

    @Query("""DELETE FROM Building""")
    suspend fun clearAllBuilding()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmenityEntity(amenity : List<AmenityEntity>)

    @Query("DELETE FROM Amenity")
    suspend fun clearAllAmenity()

    @Transaction
    @Query("""SELECT building_num, building_name
            FROM Building
            WHERE x1 <= :x AND :x <= x2
              AND y1 <= :y AND :y <= y2
    """)
    suspend fun findBuildingInfo(x: Int, y: Int): BuildingWithAmenities?
}