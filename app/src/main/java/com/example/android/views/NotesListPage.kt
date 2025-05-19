package com.example.android.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android.routes.Routes
import com.example.firebaseauth.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotesListPage(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val notes = viewModel.notes.collectAsState().value

    LaunchedEffect(Unit) { viewModel.loadNotes() }

    Column(Modifier.padding(16.dp)) {
        Text("Twoje notatki", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(notes) { note ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            viewModel.selectNote(note)
                            navController.navigate(Routes.edit_note)
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(note.content ?: "Brak tytułu", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(note.content ?: "Brak treści", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        val formattedTime = remember(note.timestamp) {
                            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(note.timestamp))
                        }
                        Text(text = "Dodano: $formattedTime", fontSize = 12.sp, color = Color.Gray)

                        Button(
                            onClick = { viewModel.deleteNote(note.id) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                        ) {
                            Text("Usuń")
                        }
                    }
                }
            }
        }
    }
}

