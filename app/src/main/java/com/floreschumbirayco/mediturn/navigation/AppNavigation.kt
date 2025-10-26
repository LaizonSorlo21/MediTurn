package com.floreschumbirayco.mediturn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.floreschumbirayco.mediturn.ui.screens.AppointmentsScreen
import com.floreschumbirayco.mediturn.ui.screens.DoctorDetailScreen
import com.floreschumbirayco.mediturn.ui.screens.DoctorsListScreen
import com.floreschumbirayco.mediturn.ui.screens.HomeScreen
import com.floreschumbirayco.mediturn.ui.screens.LoginScreen
import com.floreschumbirayco.mediturn.ui.screens.NewAppointmentScreen
import com.floreschumbirayco.mediturn.ui.screens.ProfileScreen
import com.floreschumbirayco.mediturn.ui.screens.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val NEW_APPOINTMENT = "new_appointment"
    const val DOCTOR_DETAIL = "doctor_detail"
    const val DOCTORS = "doctors"
    const val APPOINTMENTS = "appointments"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.NEW_APPOINTMENT) { NewAppointmentScreen(navController) }
        composable(Routes.DOCTOR_DETAIL) { DoctorDetailScreen(navController) }
        composable(Routes.DOCTORS) { DoctorsListScreen(navController) }
        composable(Routes.APPOINTMENTS) { AppointmentsScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
    }
}

