package com.floreschumbirayco.mediturn.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.floreschumbirayco.mediturn.model.Doctor
import com.floreschumbirayco.mediturn.navigation.Routes

@Composable
fun DoctorsListScreen(navController: NavController) {
    val doctors = listOf(
        Doctor("1", "Dra. María Pérez", "Pediatría"),
        Doctor("2", "Dr. Juan Ruiz", "Cardiología"),
        Doctor("3", "Dra. Sofía León", "Dermatología")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Médicos",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        items(doctors) { doc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { navController.navigate(Routes.DOCTOR_DETAIL) }
            ) {
                ListItem(
                    overlineContent = { Text(doc.specialty) },
                    headlineContent = { Text(doc.name) },
                    trailingContent = { Icon(Icons.Default.ArrowForward, contentDescription = null) }
                )
            }
        }
    }
}
