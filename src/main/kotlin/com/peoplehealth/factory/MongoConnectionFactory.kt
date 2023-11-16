package com.peoplehealth.factory

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class MongoConnectionFactory(
    databaseName: String = "peoplehealth",
) {

    // default connection by localhost using default port (27017)
    private val client = KMongo.createClient()

    // default database
    val database: MongoDatabase = client.getDatabase(databaseName)

    inline fun <reified T : Any> getCollection() = database.getCollection<T>()
}