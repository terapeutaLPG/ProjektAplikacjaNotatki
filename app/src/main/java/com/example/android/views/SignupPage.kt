package com.example.android.views


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.routes.Routes
import com.example.firebaseauth.viewmodel.AuthState
import com.example.firebaseauth.viewmodel.AuthViewModel
import android.util.Patterns
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(Routes.home)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()


            else -> Unit
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.kulka),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(110.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .verticalScroll(rememberScrollState()), // ← DODANE
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "◀ Back",
                    color = Color(0xFF471AA0),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )


                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "Sign Up",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF471AA0)
                )


                Spacer(modifier = Modifier.height(36.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Full Name", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.PersonOutline,
                            contentDescription = null,
                            tint = Color(0xFF471AA0)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF9747FF),
                        unfocusedBorderColor = Color(0xFF9747FF),
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true
                )


                Spacer(modifier = Modifier.height(50.dp))


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = Color(0xFF471AA0)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF9747FF),
                        unfocusedBorderColor = Color(0xFF9747FF),
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true
                )


                Spacer(modifier = Modifier.height(50.dp))


                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFF471AA0)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF9747FF),
                        unfocusedBorderColor = Color(0xFF9747FF),
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true
                )


                Spacer(modifier = Modifier.height(50.dp))


                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("Confirm Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFF471AA0)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF9747FF),
                        unfocusedBorderColor = Color(0xFF9747FF),
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true
                )


                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
                }


                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                            errorMessage = "All fields are required"
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            errorMessage = "Invalid email format"
                        } else if (password.length < 6) {
                            errorMessage = "Password must be at least 6 characters"
                        } else if (password != confirmPassword) {
                            errorMessage = "Passwords do not match"
                        } else {
                            errorMessage = ""
                            authViewModel.signup(context, email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC58BF2)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text("Sign Up", color = Color.White, fontWeight = FontWeight.Bold)
                }


                Spacer(modifier = Modifier.height(30.dp))
            }


            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account ? ",
                    fontSize = 14.sp,
                    color = Color(0xFF471AA0)
                )
                Text(
                    text = "Sign In",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF471AA0),
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.login)
                    }
                )
            }
        }
    }
}

