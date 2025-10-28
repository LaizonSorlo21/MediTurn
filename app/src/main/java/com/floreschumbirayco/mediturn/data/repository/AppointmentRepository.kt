package com.floreschumbirayco.mediturn.data.repository

import com.floreschumbirayco.mediturn.data.datasource.DataSource
import com.floreschumbirayco.mediturn.model.Appointment

class AppointmentRepository {
    fun listAppointmentsForPatient(patientId: String): List<Appointment> =
        DataSource.listAppointmentsForPatient(patientId)

    fun bookAppointment(
        patientId: String,
        doctorId: String,
        reason: String,
        slotId: String?,
        date: String?,
        time: String?
    ): Appointment = DataSource.bookAppointment(patientId, doctorId, reason, slotId, date, time)

    fun cancelAppointment(appointmentId: String): Boolean = DataSource.cancelAppointment(appointmentId)

    fun rescheduleAppointment(appointmentId: String, newSlotId: String): Appointment? =
        DataSource.rescheduleAppointment(appointmentId, newSlotId)
}
