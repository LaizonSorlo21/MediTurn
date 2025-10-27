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
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val NEW_APPOINTMENT = "new_appointment"
    const val NEW_APPOINTMENT_ROUTE = "new_appointment?doctorId={doctorId}&slotId={slotId}"
    const val DOCTOR_DETAIL = "doctor_detail"
    const val DOCTOR_DETAIL_ROUTE = "doctor_detail/{doctorId}"
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
        composable(Routes.NEW_APPOINTMENT) { NewAppointmentScreen(navController, doctorId = null, slotId = null) }
        composable(
            route = Routes.NEW_APPOINTMENT_ROUTE,
            arguments = listOf(
                navArgument("doctorId") { type = NavType.StringType; nullable = true; defaultValue = null },
                navArgument("slotId") { type = NavType.StringType; nullable = true; defaultValue = null }
            )
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId")
            val slotId = backStackEntry.arguments?.getString("slotId")
            NewAppointmentScreen(navController, doctorId = doctorId, slotId = slotId)
        }
        composable(
            route = Routes.DOCTOR_DETAIL_ROUTE,
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            DoctorDetailScreen(navController, doctorId)
        }
        composable(Routes.DOCTORS) { DoctorsListScreen(navController) }
        composable(Routes.APPOINTMENTS) { AppointmentsScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
    }
}


