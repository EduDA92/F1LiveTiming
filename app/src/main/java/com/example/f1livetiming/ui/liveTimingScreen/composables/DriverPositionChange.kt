package com.example.f1livetiming.ui.liveTimingScreen.composables

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1livetiming.R

@Composable
fun DriverPositionChange(
    modifier: Modifier = Modifier,
    positionChange: Int
) {

    if (positionChange > 0) {
        Text(
            text = stringResource(id = R.string.positive_position_change_sr, positionChange),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF229971),
            modifier = modifier.requiredWidth(35.dp)
        )
    } else if (positionChange < 0) {
        Text(
            text = positionChange.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFE80020),
            modifier = modifier.requiredWidth(35.dp)
        )
    } else {
        Text(
            text = "-",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFB6BABD),
            modifier = modifier.requiredWidth(35.dp)
        )
    }


}

@Preview
@Composable
fun PreviewDriverPositionNegativeChange() {
    DriverPositionChange(positionChange = -1)
}

@Preview
@Composable
fun PreviewDriverPositionPositiveChange() {
    DriverPositionChange(positionChange = 3)
}

@Preview
@Composable
fun PreviewDriverPositionNoChange() {
    DriverPositionChange(positionChange = 0)
}