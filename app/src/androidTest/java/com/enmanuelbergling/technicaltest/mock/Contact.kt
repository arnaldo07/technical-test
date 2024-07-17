package com.enmanuelbergling.technicaltest.mock

import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.domain.entity.Location

val FAKE_CONTACTS = listOf(
    Contact(
        gender = "male",
        name = "John",
        lastname = "Doe",
        location = Location(
            street = "123 Main St",
            city = "Springfield",
            state = "IL",
            country = "USA",
            postcode = "62701",
            coordinates = Coordinates(latitude = "39.7817", longitude = "-89.6501")
        ),
        email = "john.doe@example.com",
        dayOfBirth = "1985-01-01",
        age = 39,
        phone = "555-1234",
        cell = "555-5678",
        thumbPicture = "https://example.com/johndoe.jpg",
        nat = "US"
    ),
    Contact(
        gender = "female",
        name = "Jane",
        lastname = "Smith",
        location = Location(
            street = "456 Elm St",
            city = "Springfield",
            state = "IL",
            country = "USA",
            postcode = "62701",
            coordinates = Coordinates(latitude = "39.7817", longitude = "-89.6501")
        ),
        email = "jane.smith@example.com",
        dayOfBirth = "1990-02-02",
        age = 34,
        phone = "555-2345",
        cell = "555-6789",
        thumbPicture = "https://example.com/janesmith.jpg",
        nat = "US"
    ),
    // Agrega 8 contactos más aquí de manera similar
    Contact(
        gender = "male",
        name = "Michael",
        lastname = "Johnson",
        location = Location(
            street = "789 Oak St",
            city = "Los Angeles",
            state = "CA",
            country = "USA",
            postcode = "90001",
            coordinates = Coordinates(latitude = "34.0522", longitude = "-118.2437")
        ),
        email = "michael.johnson@example.com",
        dayOfBirth = "1982-03-03",
        age = 42,
        phone = "555-3456",
        cell = "555-7890",
        thumbPicture = "https://example.com/michaeljohnson.jpg",
        nat = "US"
    ),
    Contact(
        gender = "female",
        name = "Emily",
        lastname = "Williams",
        location = Location(
            street = "321 Pine St",
            city = "New York",
            state = "NY",
            country = "USA",
            postcode = "10001",
            coordinates = Coordinates(latitude = "40.7128", longitude = "-74.0060")
        ),
        email = "emily.williams@example.com",
        dayOfBirth = "1995-04-04",
        age = 29,
        phone = "555-4567",
        cell = "555-8901",
        thumbPicture = "https://example.com/emilywilliams.jpg",
        nat = "US"
    ),
    Contact(
        gender = "male",
        name = "David",
        lastname = "Brown",
        location = Location(
            street = "654 Maple St",
            city = "Chicago",
            state = "IL",
            country = "USA",
            postcode = "60601",
            coordinates = Coordinates(latitude = "41.8781", longitude = "-87.6298")
        ),
        email = "david.brown@example.com",
        dayOfBirth = "1988-05-05",
        age = 36,
        phone = "555-5678",
        cell = "555-9012",
        thumbPicture = "https://example.com/davidbrown.jpg",
        nat = "US"
    ),
    Contact(
        gender = "female",
        name = "Olivia",
        lastname = "Jones",
        location = Location(
            street = "987 Birch St",
            city = "Houston",
            state = "TX",
            country = "USA",
            postcode = "77001",
            coordinates = Coordinates(latitude = "29.7604", longitude = "-95.3698")
        ),
        email = "olivia.jones@example.com",
        dayOfBirth = "1992-06-06",
        age = 32,
        phone = "555-6789",
        cell = "555-0123",
        thumbPicture = "https://example.com/oliviajones.jpg",
        nat = "US"
    ),
    Contact(
        gender = "male",
        name = "James",
        lastname = "Garcia",
        location = Location(
            street = "321 Cedar St",
            city = "Phoenix",
            state = "AZ",
            country = "USA",
            postcode = "85001",
            coordinates = Coordinates(latitude = "33.4484", longitude = "-112.0740")
        ),
        email = "james.garcia@example.com",
        dayOfBirth = "1980-07-07",
        age = 44,
        phone = "555-7890",
        cell = "555-1234",
        thumbPicture = "https://example.com/jamesgarcia.jpg",
        nat = "US"
    ),
    Contact(
        gender = "female",
        name = "Sophia",
        lastname = "Martinez",
        location = Location(
            street = "543 Walnut St",
            city = "Philadelphia",
            state = "PA",
            country = "USA",
            postcode = "19019",
            coordinates = Coordinates(latitude = "39.9526", longitude = "-75.1652")
        ),
        email = "sophia.martinez@example.com",
        dayOfBirth = "1986-08-08",
        age = 38,
        phone = "555-8901",
        cell = "555-2345",
        thumbPicture = "https://example.com/sophiamartinez.jpg",
        nat = "US"
    ),
    Contact(
        gender = "male",
        name = "Christopher",
        lastname = "Lopez",
        location = Location(
            street = "876 Pine St",
            city = "San Antonio",
            state = "TX",
            country = "USA",
            postcode = "78201",
            coordinates = Coordinates(latitude = "29.4241", longitude = "-98.4936")
        ),
        email = "christopher.lopez@example.com",
        dayOfBirth = "1983-09-09",
        age = 41,
        phone = "555-9012",
        cell = "555-3456",
        thumbPicture = "https://example.com/christopherlopez.jpg",
        nat = "US"
    ),
    Contact(
        gender = "female",
        name = "Emma",
        lastname = "Wilson",
        location = Location(
            street = "123 Aspen St",
            city = "San Diego",
            state = "CA",
            country = "USA",
            postcode = "92101",
            coordinates = Coordinates(latitude = "32.7157", longitude = "-117.1611")
        ),
        email = "emma.wilson@example.com",
        dayOfBirth = "1998-10-10",
        age = 25,
        phone = "555-0123",
        cell = "555-4567",
        thumbPicture = "https://example.com/emmawilson.jpg",
        nat = "US"
    )
)