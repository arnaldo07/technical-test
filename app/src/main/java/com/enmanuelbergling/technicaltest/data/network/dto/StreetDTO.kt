package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class StreetDTO(
    @SerializedName("number")
    val number: Int,
    @SerializedName("name")
    val name: String,
) {
    fun toFormattedString() = "$number $name"
}