package com.dwinging.hanshininfo_refactoring.ui.theme

import android.R.attr.maxLines
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow



object ComponentTextStyles {

    val MainMenu: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )

    val SubMenu: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )

    val MonthText: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

    val LabelText: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

    val ValueText: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

    val MainMenuPadding = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)

    val subMenuPadding = Modifier
        .padding(start = 30.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)
        .fillMaxWidth()

    val TextPadding = Modifier.padding(5.dp)
    val LabelPadding = Modifier.padding(10.dp)
    val ValuePadding = Modifier.padding(10.dp)

    val ContentRowStyle = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
        .shadow(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            clip = false
        )
        .background(
            color = WhiteColor,
            shape = RoundedCornerShape(15.dp),
        )
        .border(
            width = 0.5.dp,
            color = BlackColor,
            shape = RoundedCornerShape(15.dp)
        )
        .padding(8.dp)


    val ContentTitleStyle = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .background(
            color = Purple80,
            shape = RoundedCornerShape(15.dp),
        )
        .border(
            width = 0.5.dp,
            color = BlackColor,
            shape = RoundedCornerShape(15.dp)
        )
        .padding(8.dp)
}