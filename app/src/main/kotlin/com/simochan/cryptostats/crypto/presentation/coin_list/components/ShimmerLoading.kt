package com.simochan.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.simochan.cryptostats.R
import com.simochan.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun ShimmerLoadingList(modifier: Modifier = Modifier) {
    val config = LocalWindowInfo.current.containerSize
    val screenHeight = config.height
    val screenWidth = config.width
    val columnItemCount = remember(screenHeight) {
        (screenHeight / 50)
    }
    val rowItemCount = remember(screenWidth) {
        (screenWidth / 160) + 2
    }

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 48.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 45.dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                maxLines = 1
            )
        }

        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                items(rowItemCount) {
                    LoadingRowItem(alpha)
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.top100),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 45.dp)
                    .padding(horizontal = 16.dp),
                maxLines = 1
            )
        }

        items(columnItemCount) {
            LoadingColumnItem(alpha)
        }
    }
}

@Composable
private fun LoadingRowItem(
    alpha: Float,
) {
    Spacer(Modifier.padding(horizontal = 4.dp))
    Box(
        modifier = Modifier
            .size(
                height = 190.dp,
                width = 180.dp
            )
            .background(
                shape = RoundedCornerShape(32.dp),
                color = Color.LightGray.copy(alpha = alpha)
            )
    )
    Spacer(Modifier.padding(horizontal = 4.dp))
}

@Composable
private fun LoadingColumnItem(
    alpha: Float,
) {
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

@PreviewLightDark()
@Composable
private fun PreviewShimmerLoading() {
    CryptoStatsTheme(true) {
        Surface {
            ShimmerLoadingList()
        }
    }
}