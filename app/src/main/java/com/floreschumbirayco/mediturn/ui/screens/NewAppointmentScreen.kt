package com.floreschumbirayco.mediturn.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.AppointmentRepository
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository
import com.floreschumbirayco.mediturn.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewAppointmentScreen(navController: NavController, doctorId: String? = null, slotId: String? = null) {
    val dni = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val specialty = remember { mutableStateOf("") }
    val doctor = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val timeState = remember { TimePickerState(9, 0, false) }
    val reason = remember { mutableStateOf("") }
    val nameError = remember { mutableStateOf(false) }
    val doctorError = remember { mutableStateOf(false) }
    val dateError = remember { mutableStateOf(false) }
    val timeError = remember { mutableStateOf(false) }
    val docRepo = remember { DoctorRepository() }
    val apptRepo = remember { AppointmentRepository() }

    LaunchedEffect(doctorId, slotId) {
        doctorId?.let {
            val d = docRepo.getDoctorById(it)
            doctor.value = d?.name ?: ""
            specialty.value = d?.specialty ?: ""
            if (slotId != null) {
                val s = docRepo.getSlotsForDoctor(it).find { s -> s.id == slotId }
                if (s != null) {
                    date.value = s.date
                    time.value = s.time
                }
            }
        }
    }
    Scaffold(topBar = { TopAppBar(title = { Text("Nueva Cita") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(dni.value, { dni.value = it }, placeholder = { Text("Documento de identidad") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(name.value, {
            name.value = it
            nameError.value = it.isBlank()
        }, isError = nameError.value, placeholder = { Text("Nombre*") }, supportingText = {
            if (nameError.value) Text("El nombre es obligatorio", style = MaterialTheme.typography.bodySmall)
        }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(specialty.value, { specialty.value = it }, placeholder = { Text("Especialidad") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(doctor.value, {
            doctor.value = it
            doctorError.value = it.isBlank()
        }, isError = doctorError.value, placeholder = { Text("Médico disponible*") }, supportingText = {
            if (doctorError.value) Text("Selecciona o escribe un médico", style = MaterialTheme.typography.bodySmall)
        }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(date.value, { }, isError = dateError.value, placeholder = { Text("Fecha${if (slotId == null) "*" else ""}") }, supportingText = {
            if (dateError.value) Text("La fecha es obligatoria", style = MaterialTheme.typography.bodySmall)
        }, modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(Modifier.height(6.dp))
        Button(onClick = { showDatePicker.value = true }, enabled = slotId == null, modifier = Modifier.fillMaxWidth()) { Text("Seleccionar Fecha") }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(time.value, { }, isError = timeError.value, placeholder = { Text("Hora${if (slotId == null) "*" else ""}") }, supportingText = {
            if (timeError.value) Text("La hora es obligatoria", style = MaterialTheme.typography.bodySmall)
        }, modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(Modifier.height(6.dp))
        Button(onClick = { showTimePicker.value = true }, enabled = slotId == null, modifier = Modifier.fillMaxWidth()) { Text("Seleccionar Hora") }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(reason.value, { reason.value = it }, placeholder = { Text("Motivo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            nameError.value = name.value.isBlank()
            doctorError.value = doctor.value.isBlank()
            if (slotId == null) {
                dateError.value = date.value.isBlank()
                timeError.value = time.value.isBlank()
            } else {
                dateError.value = false
                timeError.value = false
            }
            val valid = !nameError.value && !doctorError.value && !dateError.value && !timeError.value
            if (!valid) return@Button
            val selectedDoctorId = doctorId ?: docRepo.searchDoctors(doctor.value).firstOrNull()?.id ?: "1"
            apptRepo.bookAppointment(
                patientId = "patient-1",
                doctorId = selectedDoctorId,
                reason = reason.value,
                slotId = slotId,
                date = if (slotId == null) date.value else null,
                time = if (slotId == null) time.value else null
            )
            navController.navigate(Routes.APPOINTMENTS)
        }, modifier = Modifier.fillMaxWidth()) { Text("Agendar") }
        }
    }

    if (showDatePicker.value) {
        val dateState = androidx.compose.material3.rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = dateState.selectedDateMillis
                    if (millis != null) {
                        val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        date.value = fmt.format(Date(millis))
                        dateError.value = false
                    }
                    showDatePicker.value = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker.value = false }) { Text("Cancelar") } }
        ) {
            DatePicker(state = dateState)
        }
    }

    if (showTimePicker.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showTimePicker.value = false },
            confirmButton = {
                TextButton(onClick = {
                    val hh = timeState.hour.toString().padStart(2, '0')
                    val mm = timeState.minute.toString().padStart(2, '0')
                    time.value = "$hh:$mm"
                    timeError.value = false
                    showTimePicker.value = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showTimePicker.value = false }) { Text("Cancelar") } },
            text = { TimePicker(state = timeState) }
        )
    }
}

