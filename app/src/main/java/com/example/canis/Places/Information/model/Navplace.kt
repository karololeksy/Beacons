package com.example.canis.Places.Information.model

data class Navplace(
    val address: String,
    val beacons: List<Beacon>,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val room: Any
)