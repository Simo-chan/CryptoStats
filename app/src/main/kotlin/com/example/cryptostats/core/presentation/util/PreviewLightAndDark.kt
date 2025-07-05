package com.example.cryptostats.core.presentation.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light", showBackground = true, backgroundColor = 0xffffffff)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xff222222
)
annotation class PreviewLightAndDark