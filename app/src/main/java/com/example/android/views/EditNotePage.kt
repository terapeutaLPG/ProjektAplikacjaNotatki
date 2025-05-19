package com.example.android.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.firebaseauth.viewmodel.NotesViewModel
import com.example.android.routes.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNotePage(
    navController: NavController,
    viewModel: NotesViewModel,
    authViewModel: AuthViewModel
) {
    val selectedNote = viewModel.getSelectedNote()
    var content by remember { mutableStateOf(selectedNote?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edytuj notatkę") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wstecz")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout(navController.context)
                        navController.navigate(Routes.login) {
                            popUpTo(0)
                        }
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Wyloguj")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Wpisz treść...") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedNote = selectedNote?.copy(content = content)
                    if (updatedNote != null) {
                        viewModel.updateNote(updatedNote)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zapisz")
            }
        }
    }
}
