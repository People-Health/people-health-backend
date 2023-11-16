package com.peoplehealth.repository

import com.peoplehealth.data.User
import com.peoplehealth.data.UserLevel

interface UserRepository {

    fun saveUser(user: User)

    fun findUserByName(name: String): User?

    fun findUserByDocument(document: String): User?

    fun findUsersByPermissionLevel(permissionLevel: UserLevel): List<User>
}