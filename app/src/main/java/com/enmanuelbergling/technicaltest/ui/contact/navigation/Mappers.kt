package com.enmanuelbergling.technicaltest.ui.contact.navigation

import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.domain.entity.Location

fun Location.toDestination() = LocationArgument(
    street = street,
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
)

fun Contact.toDestination() = ContactDestination(
    gender = gender,
    name = name,
    email = email,
    dayOfBirth = dayOfBirth,
    age = age,
    phone = phone,
    cell = cell,
    thumbPicture = thumbPicture,
    nat = nat,
    location = location.toDestination(),
)

fun LocationArgument.toDomain() = Location(
    street = street,
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    coordinates = Coordinates(latitude, longitude)
)

fun ContactDestination.toDomain() = Contact(
    gender = gender,
    name = name,
    email = email,
    dayOfBirth = dayOfBirth,
    age = age,
    phone = phone,
    cell = cell,
    thumbPicture = thumbPicture,
    nat = nat,
    location = location.toDomain(),
)