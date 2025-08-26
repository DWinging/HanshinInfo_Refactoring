package com.dwinging.hanshininfo_refactoring.view.campusMap

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingWithAmenities
import com.dwinging.hanshininfo_refactoring.ui.theme.BlackColor
import com.dwinging.hanshininfo_refactoring.ui.theme.ComponentTextStyles

@Composable
fun BuildingView(info: BuildingWithAmenities?) {

    var sheetWidth by remember { mutableIntStateOf(0) }

    Column (
        modifier = Modifier.padding(10.dp)
            .onSizeChanged { size ->
                sheetWidth = size.width
            }
    ){
        val buildingNum = info?.building?.buildingNum?.toInt()?: 0
        LoadImage(info, buildingNum)
        LoadBuildingInfo(info, buildingNum)
    }
}

@Composable
private fun LoadImage(info: BuildingWithAmenities?, buildingNum: Int) {
    val buildingImg = BuildingName.fromNumber(buildingNum).imageRes

    Image(
        painter = painterResource(id = buildingImg),
        contentDescription = info?.building?.buildingName,
        modifier = Modifier
            .padding(start = 10.dp, end=10.dp)
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 0.5.dp,
                color = BlackColor,
                shape = RoundedCornerShape(20.dp)
            )
    )
}

@Composable
private fun LoadBuildingInfo(info: BuildingWithAmenities?, buildingNum: Int) {
    Column(
        modifier = ComponentTextStyles.ContentRowStyle
    ) {
        Text("건물 이름 : ${info?.building?.buildingName.toString()}")
        Text("건물 번호 : $buildingNum")
        Row {
            info?.amenities?.forEach { img ->
                val amenityName = img.amenity.toString()
                val amenityImg = AmenityName.fromName(amenityName).imageRes
                Image(
                    painter = painterResource(id = amenityImg),
                    contentDescription = null
                )
            }
        }
    }
}