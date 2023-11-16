package com.peoplehealth.service

import com.peoplehealth.data.Patient
import com.peoplehealth.data.PatientInformation
import java.util.Optional

interface PatientService {

    fun savePatient(patient: Patient)

    fun findPatientByName(name: String): Optional<Patient>

    fun findPatientByDocument(document: String): Optional<Patient>

    fun savePatientInformationByDocument(document: String, information: PatientInformation)
}