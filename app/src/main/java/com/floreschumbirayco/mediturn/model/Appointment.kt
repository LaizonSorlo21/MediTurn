package com.floreschumbirayco.mediturn.model

data class Appointment(
    val id: String,
    val patientId: String,
    val doctorId: String,
    val slotId: String?,
    val date: String,
    val time: String,
    val reason: String
)
