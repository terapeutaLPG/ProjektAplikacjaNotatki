package com.example.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.android.routes.Routes
import com.example.android.views.HomePage
import com.example.android.views.LoginPage
import com.example.android.views.SignupPage
import com.example.android.views.EditNotePage
import com.example.android.views.NotesListPage
import com.example.android.views.NotesPage
import com.example.firebaseauth.viewmodel.NotesViewModel


//@Composable
//
//fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = Routes.login, builder = {
//        composable(Routes.login) {
//            LoginPage(modifier, navController, authViewModel)
//        }
//        composable(Routes.signup) {
//            SignupPage(modifier, navController, authViewModel)
//        }
//        val notesViewModel = NotesViewModel()
//
//        composable(Routes.notes.toString()) {
//            NotesListPage(navController, notesViewModel)
//        }
//
//        composable(Routes.edit_note) {
//            EditNotePage(navController, notesViewModel)
//        }
//
//        composable(Routes.home) {
//            HomePage(
//                navController = navController,
//                authViewModel = authViewModel,
//                notesViewModel = notesViewModel
//            )
//        }
//    })
//}

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val notesViewModel = NotesViewModel()

    NavHost(navController = navController, startDestination = Routes.notes) {
        composable(Routes.login) {
            LoginPage(modifier, navController, authViewModel)
        }
        composable(Routes.signup) {
            SignupPage(modifier, navController, authViewModel)
        }
        composable(Routes.notes) {
            NotesPage(navController, notesViewModel)
        }
        composable(Routes.edit_note) {
            EditNotePage(navController, notesViewModel)
        }
        composable(Routes.home) {
            HomePage(
                navController = navController,
                authViewModel = authViewModel,
                notesViewModel = notesViewModel
            )
        }
    }
}
