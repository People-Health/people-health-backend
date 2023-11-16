package com.peoplehealth.repository

import com.peoplehealth.data.Exam
import com.peoplehealth.data.Patient
import com.peoplehealth.data.PatientInformation

interface PatientRepository {

    fun savePatient(patient: Patient)

    fun findPatientByName(name: String): Patient?

    fun findPatientByDocument(document: String): Patient?

    fun savePatientInformationByDocument(document: String, information: PatientInformation)

    fun saveExamsByPatientDocument(document: String, exams: List<Exam>)
}