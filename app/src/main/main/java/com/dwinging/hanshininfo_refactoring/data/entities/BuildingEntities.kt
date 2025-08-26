package com.dwinging.hanshininfo_refactoring.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="Building")
data class BuildingEntity(
    @PrimaryKey
    @ColumnInfo(name="building_num")
    val number: Int,

    @ColumnInfo(name="building_name")
    val name: String,

    @ColumnInfo(name="x1")
    val left: Int,

    @ColumnInfo(name="y1")
    val top: Int,

    @ColumnInfo(name="x2")
    val right: Int,

    @ColumnInfo(name="y2")
    val bottom: Int
)

@Entity(
    tableName = "Amenity",
    primaryKeys = ["building_num", "building_amenity"],
    foreignKeys = [
        ForeignKey(
            entity = BuildingEntity::class,
            parentColumns = ["building_num"],
            childColumns = ["building_num"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("building_num")]
)
data class AmenityEntity(
    @ColumnInfo(name = "building_num")
    val buildingNum: Int,

    @ColumnInfo(name="building_amenity")
    val amenity: String,
)