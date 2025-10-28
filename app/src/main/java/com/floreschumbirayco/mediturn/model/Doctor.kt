package com.floreschumbirayco.mediturn.model

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val city: String,
    val telemedicine: Boolean
)
