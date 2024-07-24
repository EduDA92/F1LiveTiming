package com.example.f1livetiming.ui.liveTimingScreen.composables


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DriverMicroSectors(
    modifier: Modifier = Modifier,
    firstMicroSectorTime: Double,
    firstMicroSectors: ImmutableList<Int>,
    secondMicroSectorTime: Double,
    secondMicroSectors: ImmutableList<Int>,
    thirdMicroSectorTime: Double,
    thirdMicroSectors: ImmutableList<Int>
) {

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        /**  First Sector */
        Column {

            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {

                firstMicroSectors.forEach {
                    Canvas(
                        modifier = Modifier
                            .size(12.dp)
                            .testTag(stringResource(id = R.string.micro_sector_color_dot_test_tag))
                    ) {
                        drawCircle(Color.fromMicroSector(it))
                    }
                }

            }

            Text(
                stringResource(id = R.string.micro_sector_sr, firstMicroSectorTime),
                fontSize = 18.sp
            )

        }

        Spacer(modifier.size(15.dp))

        /**  Second Sector */
        Column {

            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {

                secondMicroSectors.forEach {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(Color.fromMicroSector(it))
                    }
                }

            }

            Text(
                stringResource(id = R.string.micro_sector_sr, secondMicroSectorTime),
                fontSize = 18.sp
            )

        }

        Spacer(modifier.size(15.dp))

        /**  Third Sector */
        Column {

            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {

                thirdMicroSectors.forEach {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(Color.fromMicroSector(it))
                    }
                }

            }

            Text(
                stringResource(id = R.string.micro_sector_sr, thirdMicroSectorTime),
                fontSize = 18.sp
            )

        }
    }

}

fun Color.Companion.fromMicroSector(microSector: Int): Color {
    return when (microSector) {
        2048 -> Color(0xFFeab308)
        2049 -> Color(0xFF10b780)
        2051 -> Color(0xFF7b39ec)
        2064 -> Color(0xFF3b82f6)
        else -> Color(0xFF3e3e45)
    }
}


@Preview
@Composable
fun PreviewDriverMicroSectors() {

    DriverMicroSectors(
        firstMicroSectorTime = 61.500,
        firstMicroSectors = persistentListOf(0, 2048, 2049, 2050, 2051, 2052, 2064, 2068),
        secondMicroSectorTime = 46.882,
        secondMicroSectors = persistentListOf(0, 2048, 2049, 2050, 2051, 2052, 2064, 2068),
        thirdMicroSectorTime = 32.442,
        thirdMicroSectors = persistentListOf(0, 2048, 2049, 2050, 2051, 2052, 2064, 2068),
    )

}