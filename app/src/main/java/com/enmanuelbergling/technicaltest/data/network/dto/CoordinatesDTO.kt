package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class CoordinatesDTO(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)