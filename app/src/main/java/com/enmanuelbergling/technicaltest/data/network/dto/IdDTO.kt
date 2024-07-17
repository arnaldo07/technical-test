package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class IdDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String?
)