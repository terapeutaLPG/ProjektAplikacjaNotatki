
package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.android.ui.theme.AndroidTheme
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.android.MyAppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            AndroidTheme {
                MyAppNavigation(authViewModel = authViewModel)
            }
        }
    }
}
