package com.enmanuelbergling.technicaltest.domain.entity


data class Location(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Int,
    val coordinates: Coordinates,
)