package com.enmanuelbergling.technicaltest.data.network.mappers

import com.enmanuelbergling.technicaltest.data.network.dto.ContactDTO
import com.enmanuelbergling.technicaltest.data.network.dto.CoordinatesDTO
import com.enmanuelbergling.technicaltest.data.network.dto.LocationDTO
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.domain.entity.Location

internal fun ContactDTO.toDomain() = Contact(
    gender = gender,
    name = name.first,
    lastname = name.last,
    location = location.toDomain(),
    email = email,
    dayOfBirth = dayOfBirth.date,
    age = dayOfBirth.age,
    phone = phone,
    cell = cell,
    thumbPicture = picture.thumbnail,
    nat = nat
)

internal fun LocationDTO.toDomain() = Location(
    street = street.toFormattedString(),
    city = city,
    state = state,
    country = country,
    postcode = postcode,
    coordinates = coordinates.toDomain(),
)

internal fun CoordinatesDTO.toDomain() = Coordinates(
    latitude = latitude,
    longitude = longitude
)