package com.dwinging.hanshininfo_refactoring.view.campusMap

import com.dwinging.hanshininfo_refactoring.R
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingWithAmenities

data class BuildingInfoItem(
    val num: Int,
    val name: String,
    val img: Int,
    val amenity: List<Int>
)

data class Coordinate(
    val num: Int,
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
)

data class BuildingDataSheet(val state: Boolean, val buildingInfo: BuildingWithAmenities?)

enum class BuildingName(private val number: Int, val imageRes: Int) {
    BUILDING_01(1, R.drawable.image_building_01),
    BUILDING_02(2, R.drawable.image_building_02),
    BUILDING_03(3, R.drawable.image_building_03),
    BUILDING_04(4, R.drawable.image_building_04),
    BUILDING_05(5, R.drawable.image_building_05),
    BUILDING_06(6, R.drawable.image_building_06),
    BUILDING_07(7, R.drawable.image_building_07),
    BUILDING_08(8, R.drawable.image_building_08),
    BUILDING_09(9, R.drawable.image_building_09),
    BUILDING_10(10, R.drawable.image_building_10),
    BUILDING_11(11, R.drawable.image_building_11),
    BUILDING_14(14, R.drawable.image_building_14),
    BUILDING_17(17, R.drawable.image_building_17),
    BUILDING_18(18, R.drawable.image_building_18),
    BUILDING_20(20, R.drawable.image_building_20);

    companion object {
        fun fromNumber(num: Int): BuildingName =
            entries.first { it.number == num }
    }
}

enum class AmenityName(private val amenityName: String, val imageRes: Int) {
    MAN_LOUNGE("남학생휴게실", R.drawable.icon_man),
    WOMAN_LOUNGE("여학생휴게실", R.drawable.icon_woman),
    ATM("ATM", R.drawable.icon_atm),
    PRINTER("복사기", R.drawable.icon_print),
    CAFE("카페", R.drawable.icon_cafe),
    POST("우체국", R.drawable.icon_post),
    RESTAURANT("식당", R.drawable.icon_food),
    CONVENIENCE_STORE("편의점", R.drawable.icon_store);

    companion object {
        fun fromName(name: String): AmenityName =
            entries.first { it.amenityName == name}
    }
}



