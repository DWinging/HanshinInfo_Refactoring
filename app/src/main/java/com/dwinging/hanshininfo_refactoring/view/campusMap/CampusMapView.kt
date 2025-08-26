package com.dwinging.hanshininfo_refactoring.view.campusMap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.dwinging.hanshininfo_refactoring.R
import androidx.core.graphics.scale
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingDAO
import com.dwinging.hanshininfo_refactoring.data.dao.BuildingWithAmenities
import com.dwinging.hanshininfo_refactoring.data.db.AppDatabase
import com.dwinging.hanshininfo_refactoring.view.components.BottomSheetView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusMapView() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(BuildingDataSheet(false, null)) }

    val bitmap = remember {
        val original = BitmapFactory.decodeResource(context.resources, R.drawable.campus_map)
        original.scale(2800, 2000)
    }
    val imageBitmap = bitmap.asImageBitmap()

    LaunchedEffect(Unit) {
        val centerX = scrollState.maxValue / 2
        scrollState.scrollTo(centerX)
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                LoadImage(bitmap, imageBitmap, context,
                    onShowSheet = { info ->
                        showSheet = BuildingDataSheet(true, info)
                        scope.launch { sheetState.show() }
                    }
                )
            }
        }
    }

    if(showSheet.state) {
        BottomSheetView(
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                    showSheet = showSheet.copy(state = false, buildingInfo = null)
                }
            }
        ){
            showSheet.buildingInfo?.let { info ->
                BuildingView(info)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadImage(
    bitmap: Bitmap,
    imageBitmap: ImageBitmap,
    context: Context,
    onShowSheet: (BuildingWithAmenities?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val buildingDAO: BuildingDAO = remember { AppDatabase.getDatabase(context).buildingDao() }

    Image(
        bitmap = imageBitmap,
        contentDescription = "Campus_Map",
        modifier = Modifier
            .width(with(LocalDensity.current) { bitmap.width.toDp() })
            .height(with(LocalDensity.current) { bitmap.height.toDp() })
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val x = offset.x.toInt()
                    val y = offset.y.toInt()
                    scope.launch {
                        val buildingInfo = buildingDAO.findBuildingInfo(x, y)
                        if (buildingInfo != null) {
                            onShowSheet(buildingInfo)
                        }
                    }
                }
            },
        contentScale = ContentScale.Fit
    )
}

@Composable
@Preview
fun CampusMapPreview() {
    CampusMapView()
}