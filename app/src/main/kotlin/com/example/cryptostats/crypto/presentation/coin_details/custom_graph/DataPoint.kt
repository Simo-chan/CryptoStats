package com.example.cryptostats.crypto.presentation.coin_details.custom_graph

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPoint(
    val x: Float,
    val y: Float,
    val xLabel: String,
) : Parcelable