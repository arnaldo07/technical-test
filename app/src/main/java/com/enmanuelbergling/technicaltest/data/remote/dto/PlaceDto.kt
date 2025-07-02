package com.enmanuelbergling.technicaltest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlaceDto(
    // El ID único del lugar, por ejemplo: "ChIJN1t_tDeuEmsRUsoyG83frY4"
    @SerializedName("id")
    val id: String?,

    // El nombre completo y formateado de la dirección.
    // Ejemplo: "1600 Amphitheatre Parkway, Mountain View, CA 94043, USA"
    @SerializedName("formattedAddress")
    val formattedAddress: String?,

    // El nombre del lugar en el idioma solicitado.
    @SerializedName("displayName")
    val displayName: DisplayNameDto
)