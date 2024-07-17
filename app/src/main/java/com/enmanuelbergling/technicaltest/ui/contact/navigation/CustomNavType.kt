package com.enmanuelbergling.technicaltest.ui.contact.navigation

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Parcelize
@Serializable
data class LocationNavArgument(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val latitude: String,
    val longitude: String,
): Parcelable

val LocationArgumentType = object : NavType<LocationNavArgument>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): LocationNavArgument? =
        bundle.getParcelable(key, LocationNavArgument::class.java)

    override fun serializeAsValue(value: LocationNavArgument): String =
        Uri.encode(Json.encodeToString(value))

    override fun parseValue(value: String): LocationNavArgument =
        Json.decodeFromString<LocationNavArgument>(value)


    override fun put(bundle: Bundle, key: String, value: LocationNavArgument) {
        bundle.putParcelable(key, value)
    }
}