package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class RegisteredDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("age")
    val age: Int
)