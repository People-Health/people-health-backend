package com.peoplehealth.controller

import kotlinx.serialization.Serializable

class CoordsController {
    private val coordsList = mutableListOf<Coords>()

    fun addCoords(coords: String) {
        val (latitude, longitude) = coords.split(",")
        coordsList.add(Coords(latitude.toDouble(), longitude.toDouble()))
    }

    fun getCoords() = coordsList
}
@Serializable
data class Coords(val latitude: Double, val longitude: Double)