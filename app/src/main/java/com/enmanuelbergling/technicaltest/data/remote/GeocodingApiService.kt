package com.enmanuelbergling.technicaltest.data.remote

import com.enmanuelbergling.technicaltest.data.remote.post_dto.NearbySearchRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GeocodingApiService {

    @POST("v1/places:searchNearby")
    suspend fun nearbySearchNew(

        @Body request: NearbySearchRequest,
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fieldMask: String = "places.displayName,places.formattedAddress,places.id"
    ): Response<com.enmanuelbergling.technicaltest.data.remote.dto.PlacesResponse>
}