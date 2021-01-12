package com.example.canis.NavigationModule

data class NavPlace(
    val address: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val room: Any
){

    override fun toString(): String {
        return name
    }

}