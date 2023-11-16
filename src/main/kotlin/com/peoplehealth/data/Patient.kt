package com.peoplehealth.data

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.Date

data class Patient(
    @BsonId
    val id: Id<Patient>? = null,
    val document: String,

    var information: PatientInformation,
    val exams: MutableList<Exam>
)

data class PatientInformation(
    val name: String,
    val age: Int,

    val lastName: String,
    val address: String,

    val bloodType: String,
    val phone: String
)

data class Exam(
    val name: String,
    val examType: String,

    val comments: String,

    val date: Date
)