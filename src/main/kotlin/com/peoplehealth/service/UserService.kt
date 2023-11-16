package com.peoplehealth.service

import com.peoplehealth.data.User
import com.peoplehealth.data.UserLevel
import java.util.Optional

interface UserService {

    fun saveUser(user: User)

    fun findUserByName(name: String): Optional<User>

    fun findUserByDocument(document: String): Optional<User>

    fun findUsersByPermissionLevel(permissionLevel: UserLevel): List<User>
}