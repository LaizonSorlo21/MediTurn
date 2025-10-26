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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewAppointmentScreen(navController: NavController) {
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
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nueva Cita", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(dni.value, { dni.value = it }, placeholder = { Text("Documento de identidad") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(name.value, { name.value = it }, placeholder = { Text("Nombre*") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(specialty.value, { specialty.value = it }, placeholder = { Text("Especialidad") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(doctor.value, { doctor.value = it }, placeholder = { Text("Médico disponible") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(date.value, { }, placeholder = { Text("Fecha") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(Modifier.height(6.dp))
        Button(onClick = { showDatePicker.value = true }, modifier = Modifier.fillMaxWidth()) { Text("Seleccionar Fecha") }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(time.value, { }, placeholder = { Text("Hora") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(Modifier.height(6.dp))
        Button(onClick = { showTimePicker.value = true }, modifier = Modifier.fillMaxWidth()) { Text("Seleccionar Hora") }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(reason.value, { reason.value = it }, placeholder = { Text("Motivo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* save */ }, modifier = Modifier.fillMaxWidth()) { Text("Agendar") }
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
                    showTimePicker.value = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showTimePicker.value = false }) { Text("Cancelar") } },
            text = { TimePicker(state = timeState) }
        )
    }
}

