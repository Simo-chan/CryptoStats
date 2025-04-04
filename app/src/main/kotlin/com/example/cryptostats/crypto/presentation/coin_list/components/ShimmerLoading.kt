package com.example.cryptostats.crypto.presentation.coin_list.components

import android.app.LocaleConfig
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun ShimmerLoadingList(modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    val itemCount = remember(screenHeight) {
        (screenHeight / 50) + 2
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(itemCount) {
            LoadingItem()
        }
    }
}


@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite loading")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.5f at 1000
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    Row(
        modifier = Modifier
            .heightIn(min = 50.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.LightGray.copy(alpha = alpha)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.LightGray.copy(alpha = alpha)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingItem() {
    CryptoStatsTheme {
        Surface {
            LoadingItem()
        }
    }
}