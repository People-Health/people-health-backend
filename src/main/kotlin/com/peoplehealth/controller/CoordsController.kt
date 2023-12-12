package com.peoplehealth.controller

import kotlinx.serialization.Serializable

class CoordsController {
    private val coordsList = mutableMapOf<Int, Coords>()

    fun addCoords(coords: String) {
        val (lat, lng) = coords.split(",")
        coordsList.put(1, Coords(lat.toDouble(), lng.toDouble()))
    }

    fun getCoords() = coordsList
}
@Serializable
data class Coords(val lat: Double, val lng: Double)