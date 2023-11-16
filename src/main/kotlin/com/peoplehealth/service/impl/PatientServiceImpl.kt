package com.peoplehealth.service.impl

import com.peoplehealth.data.Patient
import com.peoplehealth.data.PatientInformation
import com.peoplehealth.repository.PatientRepository
import com.peoplehealth.service.PatientService
import java.util.*

class PatientServiceImpl(
    private val patientRepository: PatientRepository
): PatientService {

    override fun savePatient(patient: Patient) {
        patientRepository.savePatient(patient)
    }

    override fun findPatientByName(name: String): Optional<Patient> {
        return Optional.ofNullable(patientRepository.findPatientByName(name.lowercase(Locale.getDefault())))
    }

    override fun findPatientByDocument(document: String): Optional<Patient> {
        return Optional.ofNullable(patientRepository.findPatientByDocument(document))
    }

    override fun savePatientInformationByDocument(document: String, information: PatientInformation) {
        patientRepository.savePatientInformationByDocument(document, information)
    }
}