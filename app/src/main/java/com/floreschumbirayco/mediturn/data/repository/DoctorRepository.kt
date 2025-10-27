package com.floreschumbirayco.mediturn.data.repository

import com.floreschumbirayco.mediturn.data.datasource.DataSource
import com.floreschumbirayco.mediturn.model.Doctor
import com.floreschumbirayco.mediturn.model.Slot

class DoctorRepository {
    fun listDoctors(): List<Doctor> = DataSource.listDoctors()

    fun searchDoctors(query: String): List<Doctor> = DataSource.searchDoctors(query)

    fun getDoctorById(id: String): Doctor? = DataSource.getDoctorById(id)

    fun getSlotsForDoctor(doctorId: String): List<Slot> = DataSource.getSlotsForDoctor(doctorId)
}
