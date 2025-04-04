package com.example.cryptostats.core.presentation.util

import android.content.Context
import com.example.cryptostats.R
import com.example.cryptostats.core.domain.util.NetworkError

fun NetworkError.toDisplayableMessage(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_server
        NetworkError.SERIALIZATION_ERROR -> R.string.error_unknown
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}