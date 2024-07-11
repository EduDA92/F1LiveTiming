package com.example.f1livetiming.ui.liveTimingScreen.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1livetiming.R
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingUIState
import java.util.Locale

@Composable
fun LiveTimingHeader(
    modifier: Modifier = Modifier,
    countryCode: String,
    sessionName: String,
    circuitName: String,
    liveTimingUIState: LiveTimingUIState
) {


    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        when (countryCode.lowercase(Locale.ROOT)) {
            "brn" -> Image(
                painter = painterResource(id = R.drawable.brn),
                contentDescription = stringResource(id = R.string.brn_cd),
                modifier = Modifier.size(48.dp)
            )

            "ksa" -> Image(
                painter = painterResource(id = R.drawable.ksa),
                contentDescription = stringResource(id = R.string.ksa_cd),
                modifier = Modifier.size(48.dp)
            )

            "aus" -> Image(
                painter = painterResource(id = R.drawable.aus),
                contentDescription = stringResource(id = R.string.aus_cd),
                modifier = Modifier.size(48.dp)
            )

            "jpn" -> Image(
                painter = painterResource(id = R.drawable.jpn),
                contentDescription = stringResource(id = R.string.jpn_cd),
                modifier = Modifier.size(48.dp)
            )

            "chn" -> Image(
                painter = painterResource(id = R.drawable.chn),
                contentDescription = stringResource(id = R.string.chn_cd),
                modifier = Modifier.size(48.dp)
            )

            "usa" -> Image(
                painter = painterResource(id = R.drawable.usa),
                contentDescription = stringResource(id = R.string.usa_cd),
                modifier = Modifier.size(48.dp)
            )

            "ita" -> Image(
                painter = painterResource(id = R.drawable.ita),
                contentDescription = stringResource(id = R.string.ita_cd),
                modifier = Modifier.size(48.dp)
            )

            "mon" -> Image(
                painter = painterResource(id = R.drawable.mon),
                contentDescription = stringResource(id = R.string.mon_cd),
                modifier = Modifier.size(48.dp)
            )

            "can" -> Image(
                painter = painterResource(id = R.drawable.can),
                contentDescription = stringResource(id = R.string.can_cd),
                modifier = Modifier.size(48.dp)
            )

            "esp" -> Image(
                painter = painterResource(id = R.drawable.esp),
                contentDescription = stringResource(id = R.string.esp_cd),
                modifier = Modifier.size(48.dp)
            )

            "aut" -> Image(
                painter = painterResource(id = R.drawable.aut),
                contentDescription = stringResource(id = R.string.aut_cd),
                modifier = Modifier.size(48.dp)
            )

            "gbr" -> Image(
                painter = painterResource(id = R.drawable.gbr),
                contentDescription = stringResource(id = R.string.gbr_cd),
                modifier = Modifier.size(48.dp)
            )

            "hun" -> Image(
                painter = painterResource(id = R.drawable.hun),
                contentDescription = stringResource(id = R.string.hun_cd),
                modifier = Modifier.size(48.dp)
            )

            "bel" -> Image(
                painter = painterResource(id = R.drawable.bel),
                contentDescription = stringResource(id = R.string.bel_cd),
                modifier = Modifier.size(48.dp)
            )

            "ned" -> Image(
                painter = painterResource(id = R.drawable.ned),
                contentDescription = stringResource(id = R.string.ned_cd),
                modifier = Modifier.size(48.dp)
            )

            "aze" -> Image(
                painter = painterResource(id = R.drawable.aze),
                contentDescription = stringResource(id = R.string.aze_cd),
                modifier = Modifier.size(48.dp)
            )

            "sgp" -> Image(
                painter = painterResource(id = R.drawable.sgp),
                contentDescription = stringResource(id = R.string.sgp_cd),
                modifier = Modifier.size(48.dp)
            )

            "mex" -> Image(
                painter = painterResource(id = R.drawable.mex),
                contentDescription = stringResource(id = R.string.mex_cd),
                modifier = Modifier.size(48.dp)
            )

            "bra" -> Image(
                painter = painterResource(id = R.drawable.bra),
                contentDescription = stringResource(id = R.string.bra_cd),
                modifier = Modifier.size(48.dp)
            )

            "qat" -> Image(
                painter = painterResource(id = R.drawable.qat),
                contentDescription = stringResource(id = R.string.qat_cd),
                modifier = Modifier.size(48.dp)
            )

            "uae" -> Image(
                painter = painterResource(id = R.drawable.uae),
                contentDescription = stringResource(id = R.string.uae_cd),
                modifier = Modifier.size(48.dp)
            )

            else -> {}

        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = stringResource(id = R.string.header_text, circuitName, sessionName),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.size(15.dp))

        when (liveTimingUIState) {
            is LiveTimingUIState.Error -> {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(Color(0xFFE80020))
                }
            }

            LiveTimingUIState.Idle -> {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(Color(0xFF229971))
                }
            }

            LiveTimingUIState.Loading -> {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(Color(0xFFB6BABD))
                }
            }
        }

    }


}

@Preview
@Composable
fun PreviewLiveTimingHeader() {

    LiveTimingHeader(
        countryCode = "GBR",
        sessionName = "Race",
        circuitName = "Silverstone",
        liveTimingUIState = LiveTimingUIState.Idle
    )

}