package com.example.android.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.firebaseauth.viewmodel.NotesViewModel
import com.example.android.routes.Routes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomePage(
    navController: NavController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel
) {
    val notesState = notesViewModel.notes.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Twoje Notatki", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notesState) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = note.title ?: "Brak tytułu",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = note.content ?: "Brak treści",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = note.timestamp?.let {
                                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                                sdf.format(Date(it))
                            } ?: "Brak daty",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                authViewModel.signout(navController.context)
                navController.navigate(Routes.login) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Wyloguj się")
        }
    }
}



//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.firebaseauth.viewmodel.AuthViewModel
//import androidx.core.content.edit
//import com.example.android.routes.Routes
//import com.example.firebaseauth.viewmodel.AuthState
//@Composable
//fun HomePage(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    val context = LocalContext.current
//    var text by remember { mutableStateOf("") }
//    var savedText by remember { mutableStateOf("") }
//    val authState = authViewModel.authState.observeAsState()
//
//    LaunchedEffect(authState.value) {
//        if (authState.value is AuthState.Unauthenticated) {
//            navController.navigate(Routes.login)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Home Page", fontSize = 28.sp)
//        Spacer(modifier = Modifier.height(32.dp))
//
//        OutlinedTextField(
//            value = text,
//            onValueChange = { text = it },
//            label = { Text("Enter some data") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                val prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
//                prefs.edit().putString("saved_data", text).apply()
//                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Save Data")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                val prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
//                savedText = prefs.getString("saved_data", "") ?: ""
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Load Data")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Saved: $savedText")
//
//        Spacer(modifier = Modifier.height(32.dp))
//        Button(onClick = {
//            authViewModel.signout(context)
//        }) {
//            Text("Sign out")
//        }
//    }
//}