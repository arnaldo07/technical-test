package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class ContactDTO(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: NameDTO,
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("email")
    val email: String,
    @SerializedName("login")
    val login: LoginDTO,
    @SerializedName("dob")
    val dayOfBirth: DayOfBirthDTO,
    @SerializedName("registered")
    val registered: RegisteredDTO,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("cell")
    val cell: String,
    @SerializedName("id")
    val id: IdDTO,
    @SerializedName("picture")
    val picture: PictureDTO,
    @SerializedName("nat")
    val nat: String
)