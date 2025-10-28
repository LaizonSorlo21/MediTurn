package com.floreschumbirayco.mediturn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository
import com.floreschumbirayco.mediturn.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(navController: NavController, doctorId: String) {
    val repo = remember { DoctorRepository() }
    val doctor = repo.getDoctorById(doctorId)
    val slots = repo.getSlotsForDoctor(doctorId)
    Scaffold(topBar = { TopAppBar(title = { Text(doctor?.name ?: "Médico") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(doctor?.name ?: "", style = MaterialTheme.typography.titleMedium)
                    val meta = listOfNotNull(
                        doctor?.specialty,
                        doctor?.city,
                        if (doctor?.telemedicine == true) "Teleconsulta" else null
                    ).joinToString(" • ")
                    if (meta.isNotBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(meta, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Horarios disponibles", style = MaterialTheme.typography.titleSmall)
                    if (slots.isEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Text("No hay horarios disponibles", style = MaterialTheme.typography.bodyMedium)
                    }
                    slots.forEach { s ->
                        Spacer(Modifier.height(6.dp))
                        Button(onClick = {
                            navController.navigate("${Routes.NEW_APPOINTMENT}?doctorId=${doctorId}&slotId=${s.id}")
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("${s.date} - ${s.time}")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { navController.navigate("${Routes.NEW_APPOINTMENT}?doctorId=${doctorId}") }, modifier = Modifier.fillMaxWidth()) { Text("Agendar sin horario específico") }
                }
            }
        }
    }
}

