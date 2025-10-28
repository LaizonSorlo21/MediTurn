package com.floreschumbirayco.mediturn.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.AppointmentRepository
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(navController: NavController) {
    val apptRepo = remember { AppointmentRepository() }
    val docRepo = remember { DoctorRepository() }
    var appts by remember { mutableStateOf(apptRepo.listAppointmentsForPatient("patient-1")) }
    LaunchedEffect(Unit) {
        appts = apptRepo.listAppointmentsForPatient("patient-1")
    }
    Scaffold(topBar = { TopAppBar(title = { Text("Mis Citas") }) }) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            if (appts.isEmpty()) {
                Text("Aún no tienes citas", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(12.dp))
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(appts) { a ->
                    val doctorName = docRepo.getDoctorById(a.doctorId)?.name ?: "Médico"
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("$doctorName - ${a.date} ${a.time}")
                            if (a.reason.isNotBlank()) {
                                Spacer(Modifier.height(4.dp))
                                Text(a.reason, style = MaterialTheme.typography.bodySmall)
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = {
                                val ok = apptRepo.cancelAppointment(a.id)
                                if (ok) {
                                    appts = apptRepo.listAppointmentsForPatient("patient-1")
                                }
                            }, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
                            Spacer(Modifier.height(6.dp))
                            Button(onClick = {
                                val available = docRepo.getSlotsForDoctor(a.doctorId).firstOrNull()
                                if (available != null) {
                                    apptRepo.rescheduleAppointment(a.id, available.id)
                                    appts = apptRepo.listAppointmentsForPatient("patient-1")
                                }
                            }, modifier = Modifier.fillMaxWidth()) { Text("Reprogramar (primer horario disponible)") }
                        }
                    }
                }
            }
        }
    }
}

