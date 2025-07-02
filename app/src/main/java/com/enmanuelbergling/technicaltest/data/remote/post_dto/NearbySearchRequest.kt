package com.enmanuelbergling.technicaltest.data.remote.post_dto



// El cuerpo que enviaremos en nuestra petición POST
data class NearbySearchRequest(
    val locationRestriction: LocationRestriction,
    val rankPreference: String = "DISTANCE", // Equivalente a rankby=distance
    val maxResultCount: Int = 1, // Solo nos interesa el lugar más cercano
    val languageCode: String = "es" // Pedimos los resultados en español
)
