package com.dwinging.hanshininfo_refactoring.data.repository

import android.content.Context
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingDAO
import com.dwinging.hanshininfo_refactoring.data.entities.AmenityEntity
import com.dwinging.hanshininfo_refactoring.data.entities.BuildingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

suspend fun insertBuildingData(context: Context, buildingDAO: BuildingDAO) =
    withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("building_with_amenity.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("data")

        val buildingList = mutableListOf<BuildingEntity>()
        val amenityList = mutableListOf<AmenityEntity>()

        for(i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val number = obj.getInt("number")

            buildingList.add(
                BuildingEntity (
                    number = number,
                    name = obj.getString("name"),
                    left = obj.getInt("x1"),
                    top = obj.getInt("y1"),
                    right = obj.getInt("x2"),
                    bottom = obj.getInt("y2")
                )
            )

            val amenities = obj.getJSONArray("amenities")
            for(j in 0 until amenities.length()) {
                amenityList.add(
                    AmenityEntity(
                        buildingNum  = number,
                        amenity = amenities.getString(j)
                    )
                )
            }
        }

        buildingDAO.clearAllBuilding()
        buildingDAO.clearAllAmenity()
        buildingDAO.insertBuildingEntity(buildingList)
        buildingDAO.insertAmenityEntity(amenityList)
    }
