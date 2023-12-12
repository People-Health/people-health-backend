package com.peoplehealth.controller

import kotlinx.serialization.Serializable

class CoordsController {
    private val coordsList = mutableMapOf<Int, Coords>()

    fun addCoords(coords: String) {
        val (latitude, longitude) = coords.split(",")
        coordsList.put(1, Coords(latitude.toDouble(), longitude.toDouble()))
    }

    fun getCoords() = coordsList
}
@Serializable
data class Coords(val latitude: Double, val longitude: Double)