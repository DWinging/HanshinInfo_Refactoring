package com.dwinging.hanshininfo_refactoring.data.repository

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.dwinging.hanshininfo_refactoring.data.dao.NumberDAO
import com.dwinging.hanshininfo_refactoring.data.entities.NumberEntity
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.data.entities.PhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

suspend fun insertNumberData(context: Context, numberDAO: NumberDAO, type: NumberType) =
    withContext(Dispatchers.IO) {
        try {
            val url = context.getString(type.urlResId)
            val doc = Jsoup.connect(url).get()
            val rows = doc.select("#listFrm > div.telSearchList > table > tbody > tr")

            val numberList = mutableListOf<NumberEntity>()

            for(row in rows) {
                val tds = row.select("td")
                if(tds.size == 4) {
                    val emptyTd = Element("td").text("")
                    tds.add(2, emptyTd)
                }
                numberList.add(
                    NumberEntity(
                        type = type,
                        affiliation = tds.getOrNull(0)?.text() ?: "",
                        department = tds.getOrNull(1)?.text() ?: "",
                        name = tds.getOrNull(2)?.text() ?: "",
                        number = PhoneNumber(tds.getOrNull(3)?.text() ?: ""),
                        room = tds.getOrNull(4)?.text() ?: ""
                    )
                )
            }

            numberDAO.insertNumber(numberList)
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                AlertDialog.Builder(context)
                    .setTitle("네트워크 오류")
                    .setMessage("네트워크 연결이 불안정합니다.\n" +
                            "연결 상태를 확인한 후 다시 시도해주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인") { _, _ ->
                        // 앱 종료
                        (context as Activity).finish()
                    }
                    .show()
            }
        }
    }