package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class TimezoneDTO(
    @SerializedName("offset")
    val offset: String,
    @SerializedName("description")
    val description: String
)