package com.gamermatch.gg_date.ui.registration

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gamermatch.gg_date.domain.entity.RegistrationData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onRegistrationSuccess: () -> Unit, viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isRegistrationSuccess) {
        if (uiState.isRegistrationSuccess) {
            Toast.makeText(context, "Регистрация успешна!", Toast.LENGTH_LONG).show()
            viewModel.resetSuccess()
            onRegistrationSuccess()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Calendar.getInstance().timeInMillis
        )

        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                        viewModel.updateBirthDate(date)
                    }
                    showDatePicker = false
                }) {
                Text("OK")
            }
        }, dismissButton = {
            TextButton(onClick = { showDatePicker = false }) {
                Text("Отмена")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = { Text("Регистрация") }, navigationIcon = {
                IconButton(onClick = onRegistrationSuccess) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.emailError != null,
                supportingText = {
                    uiState.emailError?.let {
                        Text(
                            it, color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                enabled = !uiState.isLoading
            )

            // Username
            OutlinedTextField(
                value = uiState.username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text("Имя пользователя") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.usernameError != null,
                supportingText = {
                    uiState.usernameError?.let {
                        Text(
                            it, color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                enabled = !uiState.isLoading
            )

            // Password
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.passwordError != null,
                supportingText = {
                    uiState.passwordError?.let {
                        Text(
                            it, color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                enabled = !uiState.isLoading
            )

            // Confirm Password
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = { Text("Подтвердите пароль") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.confirmPasswordError != null,
                supportingText = {
                    uiState.confirmPasswordError?.let {
                        Text(
                            it, color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                enabled = !uiState.isLoading
            )

            // Birth Date
            OutlinedTextField(
                value = uiState.birthDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "",
                onValueChange = { },
                label = { Text("Дата рождения") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                isError = uiState.birthDateError != null,
                supportingText = {
                    uiState.birthDateError?.let {
                        Text(
                            it, color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Выбрать дату")
                    }
                },
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Register Button
            Button(
                onClick = {
                    val registrationData = RegistrationData(
                        email = uiState.email,
                        username = uiState.username,
                        password = uiState.password,
                        birthDate = uiState.birthDate ?: LocalDate.now()
                    )
                    viewModel.register(registrationData)
                }, modifier = Modifier.fillMaxWidth(), enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Зарегистрироваться")
                }
            }

            // Login link
            TextButton(
                onClick = { /* Переход на логин */ }) {
                Text("Уже есть аккаунт? Войти")
            }
        }
    }
}