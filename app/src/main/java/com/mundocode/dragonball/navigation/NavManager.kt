package com.mundocode.dragonball.navigation

import InicioView
import PlanetDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mundocode.dragonball.viewmodels.DragonBallListViewModel
import com.mundocode.dragonball.views.Music
import com.mundocode.dragonball.views.PlanetsView
import com.mundocode.dragonball.views.SaiyanDetailsScreen

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characters") {
        composable("characters") { InicioView(navController = navController, viewModel = DragonBallListViewModel()) }
        composable("characters/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            it.arguments?.getInt("id")?.let { id ->
                SaiyanDetailsScreen(navController = navController, id = id)
            }
        }
        composable("planets") { PlanetsView(navController) }
        composable("planets/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            it.arguments?.getInt("id")?.let { id ->
                PlanetDetailsScreen(navController = navController, id = id)
            }
        }
        composable("music") { Music(navController, context = LocalContext.current) }
    }
}
