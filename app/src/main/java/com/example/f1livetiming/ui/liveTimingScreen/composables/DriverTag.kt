package com.example.f1livetiming.ui.liveTimingScreen.composables

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1livetiming.ui.theme.RedBull

@Composable
fun DriverTag(
    modifier: Modifier = Modifier,
    driverPosition: Int,
    driverName: String,
    color: Color,
) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(4.dp))
        .background(color = color),
        verticalAlignment = Alignment.CenterVertically) {

        /** requiredWidth modifier in order to equal the size of the composable when there are double
         * digits and single digits and when the driver acronyms are different.*/

        Text(driverPosition.toString(), modifier = Modifier.padding(4.dp).requiredWidth(20.dp), color = Color.White)

        Surface(modifier = Modifier.padding(4.dp).clip(RoundedCornerShape(4.dp))) {
            Text(driverName, modifier = Modifier.padding(4.dp).requiredWidth(40.dp), textAlign = TextAlign.Center, color = color)
        }
    }

}

@Preview()
@Composable
fun DriverTagPreview() {

    DriverTag(
        driverPosition = 1,
        driverName = "VER",
        color = Color(0xFF3671C6)
    )

}