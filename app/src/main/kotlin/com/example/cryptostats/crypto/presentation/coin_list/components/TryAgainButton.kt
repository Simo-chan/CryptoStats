package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.PreviewLightAndDark
import com.example.cryptostats.crypto.presentation.coin_list.CoinListAction
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun TryAgainButton(
    message: String,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.eth_lottie)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { onAction(CoinListAction.OnRefresh) },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .padding(16.dp)
                .size(width = 150.dp, height = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@PreviewLightAndDark
@Composable
private fun TryAgainScreenPreview() {
    CryptoStatsTheme(true) {
        Surface {
            TryAgainButton(
                message = "No internet connection",
                onAction = {}
            )
        }
    }
}