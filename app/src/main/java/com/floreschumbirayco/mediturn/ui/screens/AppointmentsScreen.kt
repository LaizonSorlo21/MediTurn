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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.AppointmentRepository
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository

@Composable
fun AppointmentsScreen(navController: NavController) {
    val apptRepo = remember { AppointmentRepository() }
    val docRepo = remember { DoctorRepository() }
    val appts = apptRepo.listAppointmentsForPatient("patient-1")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis Citas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
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
                    }
                }
            }
        }
    }
}

