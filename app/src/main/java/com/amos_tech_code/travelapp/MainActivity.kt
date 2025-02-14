package com.amos_tech_code.travelapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amos_tech_code.travelapp.cache.TravelAppSession
import com.amos_tech_code.travelapp.navigation.NavRoutes
import com.amos_tech_code.travelapp.ui.feature.login.LoginScreen
import com.amos_tech_code.travelapp.ui.feature.register.RegisterScreen
import com.amos_tech_code.travelapp.ui.home.HomeScreen
import com.amos_tech_code.travelapp.ui.theme.TravelAppTheme
import org.koin.compose.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelAppTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        App(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun App(modifier: Modifier) {

    val session : TravelAppSession = getKoin().get()
    val currentScreen  = remember {
        mutableStateOf<String?>(null)
    }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        val token  = session.getToken()

        if (token.isNullOrEmpty()) {
            currentScreen.value = NavRoutes.Login.route
        } else {
            currentScreen.value = NavRoutes.Home.route
        }
    }

    currentScreen.value?.let {
        NavHost(
            navController = navController,
            startDestination = it
        ) {
            composable(NavRoutes.Login.route) {
                LoginScreen(navController)
            }

            composable(NavRoutes.SignUp.route) {
                RegisterScreen(navController)
            }

            composable(NavRoutes.Home.route) {
                HomeScreen(navController, session)
            }

            composable(NavRoutes.Loading.route) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }

            }
        }
    }

}
