package com.peoplehealth.data

import org.litote.kmongo.Id

data class User(

    val id: Id<User>? = null,

    val name: String,
    val lastName: String,
    val document: String,

    val level: UserLevel
)

enum class Permission(val permissionLevel: Int, val permission: String) {
    EXAMS(4, "Exames"),
    CREATE_PATIENTS(3, "Criar pacientes"),
    DELETE_PATIENTS(5, "Deletar pacientes"),
    CREATE_USER(5, "Criar Users"),
}

enum class UserLevel(val levelName: String) {
    NURSE("Enfermeiro"),
    RESCUER("Socorrista"),
    RECEPTIONIST("Recepcionista") ,
    MEDIC("Medico"),
    ADMINISTRATOR("Administrador");

    fun getPermission(): List<Permission> {
        return Permission.values().asList()
    }

    private fun getPermissionLevel(): Int {
        return this.ordinal + 1
    }
}
