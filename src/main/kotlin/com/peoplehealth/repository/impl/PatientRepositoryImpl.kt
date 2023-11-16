package com.peoplehealth.repository.impl

import com.peoplehealth.data.Exam
import com.peoplehealth.data.Patient
import com.peoplehealth.data.PatientInformation
import com.peoplehealth.factory.MongoConnectionFactory
import com.peoplehealth.repository.PatientRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.save

class PatientRepositoryImpl(
    mongoConnectionFactory: MongoConnectionFactory
): PatientRepository {

    private val collection = mongoConnectionFactory.getCollection<Patient>()

    override fun savePatient(patient: Patient) = collection.save(patient)

    override fun findPatientByName(name: String): Patient? = collection.findOne(PatientInformation::name eq name)

    override fun findPatientByDocument(document: String): Patient? = collection.findOne(Patient::document eq document)

    override fun savePatientInformationByDocument(document: String, information: PatientInformation) {
        findPatientByDocument(document).apply {
            this!!.information = information
            collection.save(this)
        } ?: return
    }

    override fun saveExamsByPatientDocument(document: String, exams: List<Exam>) {
        findPatientByDocument(document).apply {
            this!!.exams.addAll(exams)
            collection.save(this)
        } ?: return
    }
}