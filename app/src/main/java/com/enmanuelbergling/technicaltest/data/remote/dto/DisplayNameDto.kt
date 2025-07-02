package com.enmanuelbergling.technicaltest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DisplayNameDto(
    // El texto del nombre del lugar.
    @SerializedName("text")
    val text: String?,

    // El código de idioma del texto, ej: "en".
    @SerializedName("languageCode")
    val languageCode: String?
)
