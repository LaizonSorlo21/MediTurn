package com.floreschumbirayco.mediturn.data.datasource

import com.floreschumbirayco.mediturn.model.Appointment
import com.floreschumbirayco.mediturn.model.Doctor
import com.floreschumbirayco.mediturn.model.Slot
import java.util.UUID

object DataSource {
    private val doctors = mutableListOf(
        Doctor(id = "1", name = "Dra. María Pérez", specialty = "Pediatría", city = "Lima", telemedicine = true),
        Doctor(id = "2", name = "Dr. Juan Ruiz", specialty = "Cardiología", city = "Lima", telemedicine = false),
        Doctor(id = "3", name = "Dra. Sofía León", specialty = "Dermatología", city = "Arequipa", telemedicine = true)
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
        return doctors.filter { it.name.lowercase().contains(q) || it.specialty.lowercase().contains(q) || it.city.lowercase().contains(q) }
    }

    fun filterDoctors(specialty: String?, city: String?, telemedicine: Boolean?): List<Doctor> {
        return doctors.filter { d ->
            val s = specialty?.trim()?.lowercase().orEmpty()
            val c = city?.trim()?.lowercase().orEmpty()
            val bySpec = s.isEmpty() || d.specialty.lowercase().contains(s)
            val byCity = c.isEmpty() || d.city.lowercase().contains(c)
            val byTele = telemedicine == null || d.telemedicine == telemedicine
            bySpec && byCity && byTele
        }
    }

    fun getDoctorById(id: String): Doctor? = doctors.find { it.id == id }

    fun getSlotsForDoctor(doctorId: String): List<Slot> = slots.filter { it.doctorId == doctorId && it.available }

    fun getSlotById(id: String): Slot? = slots.find { it.id == id }

    fun bookAppointment(
        patientId: String,
        doctorId: String,
        reason: String,
        slotId: String?,
        date: String?,
        time: String?
    ): Appointment {
        var finalDate = date ?: ""
        var finalTime = time ?: ""
        if (slotId != null) {
            val slot = slots.first { it.id == slotId }
            finalDate = slot.date
            finalTime = slot.time
            val index = slots.indexOfFirst { it.id == slotId }
            if (index >= 0) slots[index] = slots[index].copy(available = false)
        }
        val appt = Appointment(
            id = UUID.randomUUID().toString(),
            patientId = patientId,
            doctorId = doctorId,
            slotId = slotId,
            date = finalDate,
            time = finalTime,
            reason = reason
        )
        appointments.add(appt)
        return appt
    }

    fun listAppointmentsForPatient(patientId: String): List<Appointment> = appointments.filter { it.patientId == patientId }

    fun cancelAppointment(appointmentId: String): Boolean {
        val idx = appointments.indexOfFirst { it.id == appointmentId }
        if (idx < 0) return false
        val appt = appointments[idx]
        if (appt.slotId != null) {
            val sIdx = slots.indexOfFirst { it.id == appt.slotId }
            if (sIdx >= 0) slots[sIdx] = slots[sIdx].copy(available = true)
        }
        appointments.removeAt(idx)
        return true
    }

    fun rescheduleAppointment(appointmentId: String, newSlotId: String): Appointment? {
        val idx = appointments.indexOfFirst { it.id == appointmentId }
        if (idx < 0) return null
        val current = appointments[idx]
        if (current.slotId != null) {
            val prevIdx = slots.indexOfFirst { it.id == current.slotId }
            if (prevIdx >= 0) slots[prevIdx] = slots[prevIdx].copy(available = true)
        }
        val newSlot = slots.firstOrNull { it.id == newSlotId } ?: return null
        val sIdx = slots.indexOfFirst { it.id == newSlotId }
        if (sIdx >= 0) slots[sIdx] = slots[sIdx].copy(available = false)
        val updated = current.copy(slotId = newSlotId, date = newSlot.date, time = newSlot.time)
        appointments[idx] = updated
        return updated
    }
}

