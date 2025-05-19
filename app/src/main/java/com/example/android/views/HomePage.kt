package com.example.android.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    val notesState by notesViewModel.notes.collectAsState()
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        notesViewModel.loadNotes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Twoje Notatki", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Nowy formularz
        OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("Tytuł notatki") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newContent,
            onValueChange = { newContent = it },
            label = { Text("Treść notatki") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newContent.isNotBlank()) {
                    notesViewModel.addNote(newTitle.ifBlank { "Bez tytułu" }, newContent)
                    newTitle = ""
                    newContent = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Dodaj")
        }

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
                            text = note.timestamp.let {
                                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                                sdf.format(Date(it))
                            },
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Button(onClick = {
                                notesViewModel.selectNote(note)
                                navController.navigate(Routes.edit_note)
                            }) {
                                Text("Edytuj")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = { notesViewModel.deleteNote(note.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Usuń")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                authViewModel.logout(navController.context)
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
