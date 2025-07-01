package com.enmanuelbergling.technicaltest.ui.feature.contact.navigation

import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.domain.entity.Location

fun Contact.toDestination() = ContactDestination(
    gender = gender,
    name = name,
    lastname = lastname,
    email = email,
    dayOfBirth = dayOfBirth,
    age = age,
    phone = phone,
    cell = cell,
    thumbPicture = thumbPicture,
    nat = nat,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    city = location.city,
    state = location.state,
    country = location.country,
)


fun ContactDestination.toDomain() = Contact(
    gender = gender,
    name = name,
    lastname = lastname,
    email = email,
    dayOfBirth = dayOfBirth,
    age = age,
    phone = phone,
    cell = cell,
    thumbPicture = thumbPicture,
    nat = nat,
    location = Location(
        street = "",
        city = city,
        state = state,
        country = country,
        postcode = "",
        coordinates = Coordinates(latitude, longitude)
    )
)