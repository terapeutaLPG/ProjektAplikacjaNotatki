package com.example.android.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseauth.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotesPage(
    navController: NavController,
    notesViewModel: NotesViewModel = viewModel()
) {
    var newNote by remember { mutableStateOf("") }
    val notes by notesViewModel.notes.collectAsState()

    LaunchedEffect(Unit) {
        notesViewModel.loadNotes()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Notatki", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            TextField(
                value = newNote,
                onValueChange = { newNote = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Nowa notatka") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (newNote.isNotBlank()) {
                    notesViewModel.addNote("Bez tytułu", newNote) // ← poprawione
                    newNote = ""
                }
            }) {
                Text("Dodaj")
            }

        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(notes.size) { index ->
                val note = notes[index]
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            note.content ?: "Brak treści",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))

                        val formattedTime = remember(note.timestamp) {
                            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(
                                Date(
                                    note.timestamp
                                )
                            )
                        }

                        Text(
                            text = "Utworzono: $formattedTime",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(8.dp))
                        Row {
                            Button(onClick = {
                                notesViewModel.selectNote(note)
                                navController.navigate("edit_note")
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

    }
}



