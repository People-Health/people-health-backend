package com.peoplehealth.repository

import com.peoplehealth.data.User

interface UserRepository {

    fun saveUser(user: User)

    fun findUserByName(name: String): User?

    fun findUserByDocument(document: String): User?

    fun findUsersByPermissionLevel(permissionLevel: Int): List<User>
}