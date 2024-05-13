package com.mundocode.dragonball.routes

sealed class Routes(val route: String) {
    object Personajes : Routes("characters")
    object Planetas : Routes("planets")
}