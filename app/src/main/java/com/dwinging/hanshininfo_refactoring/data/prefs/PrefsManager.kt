package com.dwinging.hanshininfo_refactoring.data.prefs

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import androidx.core.content.edit

object PrefsManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    // FirstLaunch
    fun isFirstLaunch(): Boolean =
        prefs.getBoolean("is_first_launch", true)

    fun setFirstLaunchDone() {
        prefs.edit { putBoolean("is_first_launch", false) }
    }

    // VersionCheck
    fun loadVersionFromAssets(context: Context, filename: String = "building_with_amenity.json"): Int {
        val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getInt("version")
    }

    fun checkBuildingDataVersion(newVersion: Int): Boolean{
        val version = prefs.getInt("building_version", 1)
        return version == newVersion
    }

    fun setBuildingVersion(newVersion: Int) {
        prefs.edit { putInt("building_version", newVersion) }
    }

    // Date - Schedule
    fun checkUpdateSchedule(): Int =
        prefs.getInt("last_update_schedule", 0)

    fun setUpdateYearSchedule(year: Int) {
        prefs.edit { putInt("last_update_schedule", year)}
    }

    // Date - Number
    fun checkUpdateNumber(): String? =
        prefs.getString("last_update_number", null)

    fun setUpdateDateNumber(date: String) {
        prefs.edit { putString("last_update_number", date)}
    }
}