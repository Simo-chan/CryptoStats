package com.example.cryptostats.crypto.presentation.coin_details.components

import android.R.attr.label
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptostats.CryptoStatsApp
import com.example.cryptostats.R
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun InfoCard(
    title: String,
    formattedText: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    formattedTextStyle: TextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = contentColor
    ),
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 15.dp, shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.cardColors(
            contentColor = contentColor,
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        AnimatedContent(
            targetState = icon,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            label = "IconAnimation"
        ) { icon ->
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(75.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun InfoCardPreview() {
    CryptoStatsTheme {
        Surface {
            InfoCard(
                title = "Price",
                formattedText = "$ 60,000.00",
                icon = ImageVector.vectorResource(id = R.drawable.dollar)
            )
        }
    }
}