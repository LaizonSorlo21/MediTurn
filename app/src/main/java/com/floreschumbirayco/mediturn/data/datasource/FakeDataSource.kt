package com.floreschumbirayco.mediturn.data.datasource

import com.floreschumbirayco.mediturn.model.Appointment
import com.floreschumbirayco.mediturn.model.Doctor
import com.floreschumbirayco.mediturn.model.Slot
import java.util.UUID

object DataSource {
    private val doctors = mutableListOf(
        Doctor(id = "1", name = "Dra. María Pérez", specialty = "Pediatría"),
        Doctor(id = "2", name = "Dr. Juan Ruiz", specialty = "Cardiología"),
        Doctor(id = "3", name = "Dra. Sofía León", specialty = "Dermatología")
    )

    private val slots = mutableListOf(
        Slot(id = "s1", doctorId = "1", date = "30/10/2025", time = "09:00"),
        Slot(id = "s2", doctorId = "1", date = "30/10/2025", time = "10:00"),
        Slot(id = "s3", doctorId = "2", date = "30/10/2025", time = "12:30"),
        Slot(id = "s4", doctorId = "3", date = "31/10/2025", time = "15:00")
    )

    private val appointments = mutableListOf<Appointment>()

    fun listDoctors(): List<Doctor> = doctors.toList()

    fun searchDoctors(query: String): List<Doctor> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return listDoctors()
        return doctors.filter { it.name.lowercase().contains(q) || it.specialty.lowercase().contains(q) }
    }

    fun getDoctorById(id: String): Doctor? = doctors.find { it.id == id }

    fun getSlotsForDoctor(doctorId: String): List<Slot> = slots.filter { it.doctorId == doctorId && it.available }

    fun getSlotById(id: String): Slot? = slots.find { it.id == id }

    fun bookAppointment(
        patientId: String,
        doctorId: String,
        reason: String,
        slotId: String?
    ): Appointment {
        val date: String
        val time: String
        if (slotId != null) {
            val slot = slots.first { it.id == slotId }
            date = slot.date
            time = slot.time
            val index = slots.indexOfFirst { it.id == slotId }
            if (index >= 0) slots[index] = slots[index].copy(available = false)
        } else {
            date = "" 
            time = "" 
        }
        val appt = Appointment(
            id = UUID.randomUUID().toString(),
            patientId = patientId,
            doctorId = doctorId,
            slotId = slotId,
            date = date,
            time = time,
            reason = reason
        )
        appointments.add(appt)
        return appt
    }

    fun listAppointmentsForPatient(patientId: String): List<Appointment> = appointments.filter { it.patientId == patientId }
}
