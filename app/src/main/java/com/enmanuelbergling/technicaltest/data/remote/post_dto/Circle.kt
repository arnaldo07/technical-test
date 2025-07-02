package com.enmanuelbergling.technicaltest.data.remote.post_dto

data class Circle(
    val center: Center,
    val radius: Double = 50.0 // Un radio pequeño, ya que ordenamos por distancia
)
