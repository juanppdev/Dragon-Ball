package com.mundocode.dragonball.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mundocode.dragonball.views.InicioView
import com.mundocode.dragonball.views.PokemonDetailsScreen

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characters") {
        composable("characters") { InicioView(navController) }
        composable("characters/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            it.arguments?.getInt("id")?.let { id ->
                PokemonDetailsScreen(navController = navController, id = id)
            }
        }
    }
}