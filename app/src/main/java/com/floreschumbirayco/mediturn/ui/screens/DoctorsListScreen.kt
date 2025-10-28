package com.floreschumbirayco.mediturn.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.data.repository.DoctorRepository
import com.floreschumbirayco.mediturn.navigation.Routes
import androidx.compose.material3.OutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsListScreen(navController: NavController) {
    val repo = remember { DoctorRepository() }
    var query by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var teleOnly by remember { mutableStateOf(false) }
    val doctors = remember(query, specialty, city, teleOnly) {
        val spec = if (specialty.isNotBlank()) specialty else if (query.isNotBlank()) query else null
        val c = if (city.isNotBlank()) city else null
        val tele = if (teleOnly) true else null
        if (spec == null && c == null && tele == null) repo.listDoctors() else repo.filterDoctors(spec, c, tele)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Médicos") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por nombre o especialidad") }
            )
            }
            item {
            OutlinedTextField(
                value = specialty,
                onValueChange = { specialty = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text("Filtrar por especialidad") }
            )
            }
            item {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text("Filtrar por ciudad") }
            )
            }
            item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)) {
                Checkbox(checked = teleOnly, onCheckedChange = { teleOnly = it })
                Text("Solo teleconsulta", modifier = Modifier.padding(top = 12.dp, start = 4.dp))
            }
            }
            if (doctors.isEmpty()) {
                item {
                    Text("No se encontraron médicos", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 12.dp))
                }
            }
            items(doctors) { doc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { navController.navigate("${Routes.DOCTOR_DETAIL}/${doc.id}") }
            ) {
                ListItem(
                    overlineContent = { Text("${doc.specialty} • ${doc.city}${if (doc.telemedicine) " • Teleconsulta" else ""}") },
                    headlineContent = { Text(doc.name) },
                    trailingContent = { Icon(Icons.Default.ArrowForward, contentDescription = null) }
                )
            }
            }
        }
    }
}

