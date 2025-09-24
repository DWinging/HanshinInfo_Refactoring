package com.dwinging.hanshininfo_refactoring.view.number

import android.content.Context
import com.dwinging.hanshininfo_refactoring.data.db.AppDatabase
import com.dwinging.hanshininfo_refactoring.data.entities.NumberDetail
import com.dwinging.hanshininfo_refactoring.data.entities.NumberList
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType

data class NumberDataSheet(
    val state: Boolean,
    val id: Int
)

suspend fun searchNumbers(type: NumberType, query: String, context: Context): List<NumberList> {
    val numberDAO = AppDatabase.getDatabase(context).numberDao()
    return numberDAO.getNumbersByQuery(type, query)
}

suspend fun getNumberDetail(id: Int, type: NumberType, context: Context): NumberDetail? {
    val numberDAO = AppDatabase.getDatabase(context).numberDao()
    return numberDAO.findNumberDetail(id, type)
}