package com.peoplehealth.factory

import com.peoplehealth.data.Patient
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class MongoConnectionFactory(
    databaseName: String = "peoplehealth",
) {

    // default connection by localhost using default port (27017)
    private val client = KMongo.createClient()

    // default database
    private val database = client.getDatabase(databaseName)

    fun <T> getCollection() = database.getCollection<Patient>()
}