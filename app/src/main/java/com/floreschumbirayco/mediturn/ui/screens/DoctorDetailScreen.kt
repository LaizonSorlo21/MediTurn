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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository
import com.floreschumbirayco.mediturn.navigation.Routes

@Composable
fun DoctorDetailScreen(navController: NavController, doctorId: String) {
    val repo = remember { DoctorRepository() }
    val doctor = repo.getDoctorById(doctorId)
    val slots = repo.getSlotsForDoctor(doctorId)
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Médico", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Replace with real image asset later
                // Image(painter = painterResource(id = R.drawable.doctor_placeholder), contentDescription = null)
                Text(doctor?.name ?: "")
                Text(doctor?.specialty ?: "")
                Spacer(Modifier.height(8.dp))
                Text("Horarios disponibles")
                slots.forEach { s ->
                    Spacer(Modifier.height(4.dp))
                    Button(onClick = {
                        navController.navigate("${Routes.NEW_APPOINTMENT}?doctorId=${doctorId}&slotId=${s.id}")
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("${s.date} - ${s.time}")
                    }
                }
                Spacer(Modifier.height(12.dp))
                Button(onClick = { navController.navigate("${Routes.NEW_APPOINTMENT}?doctorId=${doctorId}") }) { Text("Agendar sin horario específico") }
            }
        }
    }
}

