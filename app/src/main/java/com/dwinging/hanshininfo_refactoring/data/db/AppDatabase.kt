package com.dwinging.hanshininfo_refactoring.data.db

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dwinging.hanshininfo_refactoring.data.converter.NumberTypeConverter
import com.dwinging.hanshininfo_refactoring.data.converter.LocalDateConverter
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingDAO
import com.dwinging.hanshininfo_refactoring.data.dao.NumberDAO
import com.dwinging.hanshininfo_refactoring.data.dao.ScheduleDAO
import com.dwinging.hanshininfo_refactoring.data.entities.AmenityEntity
import com.dwinging.hanshininfo_refactoring.data.entities.BuildingEntity
import com.dwinging.hanshininfo_refactoring.data.entities.NumberEntity
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import com.dwinging.hanshininfo_refactoring.data.entities.ScheduleEntity
import com.dwinging.hanshininfo_refactoring.data.prefs.PrefsManager
import com.dwinging.hanshininfo_refactoring.data.repository.insertBuildingData
import com.dwinging.hanshininfo_refactoring.data.repository.insertNumberData
import com.dwinging.hanshininfo_refactoring.data.repository.insertScheduleData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Database(
    entities = [
        BuildingEntity::class,
        AmenityEntity::class,
        NumberEntity::class,
        ScheduleEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocalDateConverter::class, NumberTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun buildingDao(): BuildingDAO
    abstract fun numberDao(): NumberDAO
    abstract fun scheduleDao(): ScheduleDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Composable
fun CheckBuildingData(context: Context) {
    val newVersion = PrefsManager.loadVersionFromAssets(context)
    val needInsert = PrefsManager.isFirstLaunch() || !PrefsManager.checkBuildingDataVersion(newVersion)

    val buildingDAO: BuildingDAO = remember { AppDatabase.getDatabase(context).buildingDao() }
    LaunchedEffect(needInsert) {
        insertBuildingData(context, buildingDAO)
        PrefsManager.setFirstLaunchDone()
        PrefsManager.setBuildingVersion(newVersion)
    }
}

@Composable
fun CheckLastUpdateSchedule(context: Context) {
    val scheduleDAO: ScheduleDAO = remember { AppDatabase.getDatabase(context).scheduleDao() }
    val date = LocalDate.now()
    val year = date.year
    val lastUpdateYear = PrefsManager.checkUpdateSchedule()
    LaunchedEffect(year != lastUpdateYear) {
        scheduleDAO.clearAllSchedule()
        insertScheduleData(context, scheduleDAO)

        PrefsManager.setUpdateYearSchedule(year)
    }
}

@Composable
fun CheckLastUpdateNumber(context: Context) {
    val numberDAO: NumberDAO = remember { AppDatabase.getDatabase(context).numberDao() }
    val today = LocalDate.now()
    val lastRecordStr = PrefsManager.checkUpdateNumber()
    val lastRecord: LocalDate? = lastRecordStr?.let { LocalDate.parse(it) }
    LaunchedEffect(lastRecord == null || lastRecord.isBefore(today)) {
        numberDAO.clearAllNumber()
        for(type in NumberType.entries) {
            insertNumberData(context, numberDAO, type)
        }

        PrefsManager.setUpdateDateNumber(today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).toString())
    }
}