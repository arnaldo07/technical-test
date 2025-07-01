package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class LocationDTO(
    @SerializedName("street")
    val street: StreetDTO,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("coordinates")
    val coordinates: CoordinatesDTO,
    @SerializedName("timezone")
    val timezone: TimezoneDTO
)