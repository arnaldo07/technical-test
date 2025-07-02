package com.enmanuelbergling.technicaltest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    // El JSON tiene un campo "places" que es un array de objetos Place.
    @SerializedName("places")
    val places: List<PlaceDto>
)
