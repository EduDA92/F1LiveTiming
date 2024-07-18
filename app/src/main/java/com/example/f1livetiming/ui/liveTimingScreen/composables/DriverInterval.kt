package com.example.f1livetiming.ui.liveTimingScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1livetiming.R

@Composable
fun DriverInterval(
    modifier: Modifier = Modifier,
    gapToLeader: String,
    interval: String
){

    Column(modifier = modifier.requiredWidth(80.dp)) {

        if(interval == "" || interval == "0.0"){
            Text("-- ---", fontSize = 18.sp)
        } else {
            if(interval.contains("L")){
                Text(interval, fontSize = 18.sp)
            } else {
                Text(stringResource(id = R.string.interval_sr, interval), fontSize = 18.sp)
            }
        }

        if(gapToLeader == "" || gapToLeader == "0.0"){
            Text("-- ---", fontSize = 12.sp, color = Color.Gray)
        } else {
            if(gapToLeader.contains("L")){
                Text(gapToLeader, fontSize = 12.sp, color = Color.Gray)
            } else {
                Text(stringResource(id = R.string.interval_sr, gapToLeader), fontSize = 12.sp, color = Color.Gray)
            }
        }

    }

}

@Preview
@Composable
fun PreviewDriverInterval(){

    DriverInterval(gapToLeader = "1L", interval = "189.999")

}

@Preview
@Composable
fun PreviewNullDriverInterval(){

    DriverInterval(gapToLeader = "", interval = "")

}

@Preview
@Composable
fun PreviewLeaderDriverInterval(){

    DriverInterval(gapToLeader = "0.0", interval = "0.0")

}