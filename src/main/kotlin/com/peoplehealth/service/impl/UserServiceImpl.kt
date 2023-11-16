package com.peoplehealth.service.impl

import com.peoplehealth.data.User
import com.peoplehealth.data.UserLevel
import com.peoplehealth.exception.ApplicationException
import com.peoplehealth.exception.Error
import com.peoplehealth.repository.UserRepository
import com.peoplehealth.service.UserService
import java.util.*

class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    @Throws(ApplicationException::class)
    override fun saveUser(user: User) {
        userRepository.runCatching {
            saveUser(user)
        }.onFailure {
            throw ApplicationException(error = Error.INVALID_USER)
        }
    }

    @Throws(ApplicationException::class)
    override fun findUserByName(name: String): Optional<User> =
        Optional.ofNullable(userRepository.findUserByName(name))

    override fun findUserByDocument(document: String): Optional<User> =
        Optional.ofNullable(userRepository.findUserByDocument(document))

    override fun findUsersByPermissionLevel(permissionLevel: UserLevel): List<User> =
        userRepository.findUsersByPermissionLevel(permissionLevel)
}