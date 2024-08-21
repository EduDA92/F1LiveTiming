package com.example.f1livetiming.ui.radiosScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DriverHeader(
    modifier: Modifier = Modifier,
    driverAcronym: String,
    teamColor: Color
) {

     Surface(modifier = modifier
         .clip(RoundedCornerShape(4.dp))
         .background(color = teamColor)
         .padding(4.dp)
         .clip(RoundedCornerShape(4.dp))) {
         Text(driverAcronym, modifier = Modifier
             .padding(4.dp)
             .requiredWidth(40.dp), textAlign = TextAlign.Center, color = teamColor)
     }

}

@Preview
@Composable
fun DriverHeaderPreview() {

    DriverHeader(driverAcronym = "VER", teamColor = Color(0xFF3671C6))

}