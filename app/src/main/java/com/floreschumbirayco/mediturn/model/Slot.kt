package com.floreschumbirayco.mediturn.model

data class Slot(
    val id: String,
    val doctorId: String,
    val date: String,
    val time: String,
    val available: Boolean = true
)
