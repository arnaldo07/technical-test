package com.enmanuelbergling.technicaltest.data.repository

import com.enmanuelbergling.technicaltest.BuildConfig
import com.enmanuelbergling.technicaltest.data.remote.GeocodingApiService
import com.enmanuelbergling.technicaltest.data.remote.post_dto.Center
import com.enmanuelbergling.technicaltest.data.remote.post_dto.Circle
import com.enmanuelbergling.technicaltest.data.remote.post_dto.LocationRestriction
import com.enmanuelbergling.technicaltest.data.remote.post_dto.NearbySearchRequest
import com.enmanuelbergling.technicaltest.viewmodel.GeocodeResult
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

data class PlaceDetails(
    val name: String?,
    val address: String
)

@Singleton
class GeocodingRepository @Inject constructor(
    private val apiService: GeocodingApiService
) {
    suspend fun getPlaceDetails(latLng: LatLng): GeocodeResult<PlaceDetails> {
        val request = NearbySearchRequest(
            locationRestriction = LocationRestriction(
                circle = Circle(
                    center = Center(
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    )
                )
            )
        )

        return try {
            val response = apiService.nearbySearchNew(
                request = request,
                apiKey = BuildConfig.MAPS_API_KEY
            )


            if (response.isSuccessful && response.body()?.places?.isNotEmpty() == true) {
                val nearestPlace = response.body()!!.places?.get(0)
                val placeDetails = PlaceDetails(
                    name = nearestPlace?.displayName?.text,
                    address = nearestPlace?.formattedAddress ?: "Dirección no disponible"
                )
                GeocodeResult.Success(placeDetails)
            } else {
                GeocodeResult.NoAddressFound(latLng)
            }
        } catch (e: Exception) {
            GeocodeResult.NetworkError()
        }
    }
}


