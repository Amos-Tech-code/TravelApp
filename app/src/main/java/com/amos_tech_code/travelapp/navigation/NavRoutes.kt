package com.amos_tech_code.travelapp.navigation

sealed class NavRoutes(val route: String) {

    data object Loading : NavRoutes("loading")

    data object Login : NavRoutes("login")

    data object SignUp : NavRoutes("register")

    data object Home : NavRoutes("home")
}