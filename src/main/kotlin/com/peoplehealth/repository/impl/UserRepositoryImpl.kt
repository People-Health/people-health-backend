package com.peoplehealth.repository.impl

import com.peoplehealth.data.User
import com.peoplehealth.data.UserLevel
import com.peoplehealth.factory.MongoConnectionFactory
import com.peoplehealth.repository.UserRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.save

class UserRepositoryImpl(
    mongoConnectionFactory: MongoConnectionFactory
): UserRepository {

    private val collection = mongoConnectionFactory.getCollection<User>()

    override fun saveUser(user: User) = collection.save(user)

    override fun findUserByName(name: String): User? = collection.findOne(User::name eq name)

    override fun findUserByDocument(document: String): User? = collection.findOne(User::document eq document)

    override fun findUsersByPermissionLevel(permissionLevel: UserLevel): List<User> = collection
        .find(User::level eq permissionLevel).toList()
}