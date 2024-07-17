package com.enmanuelbergling.technicaltest.domain.entity

data class Contact(
    val gender: String,
    val name: String,
    val location: Location,
    val email: String,
    val dayOfBirth: String,
    val age: Int,
    val phone: String,
    val cell: String,
    val thumbPicture: String
)
