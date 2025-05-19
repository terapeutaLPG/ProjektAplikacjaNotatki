
package com.example.android.views

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.routes.Routes
import com.example.firebaseauth.viewmodel.AuthState
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.firebaseauth.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authStatus = authViewModel.authState.observeAsState()

    LaunchedEffect(authStatus.value) {
        when (val state = authStatus.value) {
            is AuthState.Authenticated -> navController.navigate(Routes.home)
            is AuthState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 19.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(62.dp))

        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier.size(129.dp)
        )

        Spacer(modifier = Modifier.height(21.dp))

        Text(
            text = "Sign in",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF471AA0),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 0.dp)
        )

        Spacer(modifier = Modifier.height(52.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            placeholder = {
                Text("Email", color = Color(0xFFB0B0B0), fontSize = 16.sp)
            },
            leadingIcon = {
                Icon(Icons.Filled.PersonOutline, contentDescription = null, tint = Color(0xFF471AA0))
            },
            modifier = Modifier
                .width(390.dp)
                .height(50.dp),
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                unfocusedBorderColor = Color(0xFF9747FF),
                focusedBorderColor = Color(0xFF9747FF),
                cursorColor = Color(0xFF9747FF),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text("Password", color = Color(0xFFB0B0B0), fontSize = 16.sp)
            },
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFF471AA0))
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .width(390.dp)
                .height(50.dp),
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                unfocusedBorderColor = Color(0xFF9747FF),
                focusedBorderColor = Color(0xFF9747FF),
                cursorColor = Color(0xFF9747FF),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.width(390.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forget Password ?",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF471AA0)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Email i hasło nie mogą być puste"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Nieprawidłowy format e-maila"
                } else {
                    errorMessage = ""
                    authViewModel.login(context, email.trim(), password)
                }

            },
            modifier = Modifier
                .width(390.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC58BF2)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Sign in", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(75.dp))
        Spacer(modifier = Modifier.height(103.dp))

        val annotatedString = buildAnnotatedString {
            append("Don't have account ? ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Sign Up")
            }
        }
        Text(
            text = annotatedString,
            color = Color(0xFF471AA0),
            modifier = Modifier.clickable {
                navController.navigate(Routes.signup)
            }
        )
    }
}

