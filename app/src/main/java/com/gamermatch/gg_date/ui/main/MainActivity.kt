package com.gamermatch.gg_date.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gamermatch.gg_date.R
import com.gamermatch.gg_date.presentation.theme.GG_DateTheme
import com.gamermatch.gg_date.ui.registration.RegistrationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GG_DateTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun Greeting() {
    Text(
        text = stringResource(R.string.app_name)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onRegisterClick = {navController.navigate("registration")}
            )
        }

        composable("registration") {
            RegistrationScreen(
                onRegistrationSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainScreen(onRegisterClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Greeting()
            MainButtons(onRegisterClick)
        }
    }
}


@Composable
fun MainButtons(onRegisterClick: () -> Unit) {
    Row {
        Button(onClick = onRegisterClick) {
            Text(stringResource(R.string.register))

        }
        Button(onClick = {}) { Text(stringResource(R.string.about)) }
        Button(onClick = {}) { Text(stringResource(R.string.main_screen)) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GG_DateTheme {
        Greeting()
    }
}