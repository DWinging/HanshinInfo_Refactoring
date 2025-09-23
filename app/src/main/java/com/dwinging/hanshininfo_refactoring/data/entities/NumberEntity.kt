package com.dwinging.hanshininfo_refactoring.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwinging.hanshininfo_refactoring.R

enum class NumberType(val urlResId: Int) {
    NUMBER_EXT(R.string.number_ext_url), // 내선
    NUMBER_PRO(R.string.number_pro_url);  // 교수
}

@Entity(tableName="Number")
data class NumberEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name="type")
    val type: NumberType,

    @ColumnInfo(name="affiliation")
    val affiliation: String,

    @ColumnInfo(name="department")
    val department: String,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="number")
    val number: String,

    @ColumnInfo(name="room")
    val room: String
)

data class NumberList(
    val id: Int,
    val affiliation: String,
    val department: String,
    val name: String,
    val number: String
)

data class NumberDetail(
    val affiliation: String,
    val department: String,
    val name: String,
    val number: String,
    val room: String
)