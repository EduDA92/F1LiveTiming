package com.example.f1livetiming.ui.radiosScreen.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1livetiming.R
import com.example.f1livetiming.ui.radiosScreen.RadiosUIState

@Composable
fun RadiosHeader(
    modifier: Modifier = Modifier,
    radiosUIState: RadiosUIState
) {

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.team_radios), fontSize = 24.sp)

        Spacer(modifier = Modifier.size(15.dp))

        when (radiosUIState) {
            is RadiosUIState.Error -> {
                Canvas(modifier = Modifier
                    .size(12.dp)
                    .testTag(stringResource(id = R.string.error_test_tag))) {
                    drawCircle(Color(0xFFE80020))
                }
            }

            RadiosUIState.Idle -> {
                Canvas(modifier = Modifier
                    .size(12.dp)
                    .testTag(stringResource(id = R.string.idle_test_tag))) {
                    drawCircle(Color(0xFF229971))
                }
            }

            RadiosUIState.Loading -> {
                Canvas(modifier = Modifier
                    .size(12.dp)
                    .testTag(stringResource(id = R.string.loading_test_tag))) {
                    drawCircle(Color(0xFFB6BABD))
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewRadiosHeader() {

    RadiosHeader(
        radiosUIState = RadiosUIState.Idle
    )

}

@Preview
@Composable
fun PreviewRadiosHeaderError() {

    RadiosHeader(
        radiosUIState = RadiosUIState.Error("")
    )

}

@Preview
@Composable
fun PreviewRadiosHeaderLoading() {

    RadiosHeader(
        radiosUIState = RadiosUIState.Loading
    )

}