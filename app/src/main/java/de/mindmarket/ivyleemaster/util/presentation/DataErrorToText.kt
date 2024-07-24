package de.mindmarket.ivyleemaster.util.presentation

import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.util.domain.DataError

fun DataError.asUiText():UiText {
    return when(this) {
        DataError.Network.BAD_REQUEST -> UiText.StringResource(R.string.error_bad_request)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server_error)
        DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.error_unauthorized)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
    }
}