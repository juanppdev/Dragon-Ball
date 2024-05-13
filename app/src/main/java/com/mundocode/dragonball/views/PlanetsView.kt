package com.mundocode.dragonball.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mundocode.dragonball.R
import com.mundocode.dragonball.viewmodels.PokemonListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlanetsView(
    navController: NavController,
    viewModel: PokemonListViewModel = viewModel()
) {
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val planetsList by viewModel.planetList.collectAsState()

    LaunchedEffect(planetsList) {
        viewModel.getPlanets()
    }

    val scaffoldState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

        Scaffold(
            containerColor = Color.Black,
            topBar = {
                MyTopAppBar()
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = scaffoldState,
                    snackbar = { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = Color.LightGray,
                            contentColor = Color.Blue
                        )
                    }
                )
            },
            bottomBar = { Planetas(navController) }
        ) {
            // Aqui va el contenido
            if (errorMessage.value != null) {
                ErrorState()
            } else if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(200.dp),
                        color = Color.Green,
                        trackColor = Color.Magenta
                    )
                }
            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(R.drawable.background), // Reemplaza con el nombre de tu imagen
                        contentDescription = "background_image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0.3f)
                    )

                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                        planetsList?.ListPlanets?.let { it ->
                            items(it.size) {
                                AsyncImage(url = planetsList!!.ListPlanets[it].image)
                                Log.d("Juan", planetsList!!.ListPlanets[it].name)
                            }
                        }
                    }

                }
            }
        }
    }
}


@Composable
private fun AsyncImage(url: String) {
    val painter: Painter = // Optionally, you can apply transformations
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // Optionally, you can apply transformations
                    transformations()
                }).build()
        )

    Image(
        modifier = Modifier
            .width(300.dp)
            .height(400.dp),
        painter = painter,
        contentDescription = null
    )
}

@Composable
fun Planetas(navController: NavController) {
    var index by remember {
        mutableIntStateOf(1)
    }

    NavigationBar(contentColor = Color.White, containerColor = Color.Red) {
        NavigationBarItem(selected = index == 0, onClick = { navController.navigate("characters") }, icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Personajes")
        }, label = { Text(text = "Personajes") })
        NavigationBarItem(selected = index == 1, onClick = { index = 1 }, icon = {
            Icon(painterResource(id = R.drawable.planet), contentDescription = "Planetas")
        }, label = { Text(text = "Planetas") })
        NavigationBarItem(selected = index == 2, onClick = { index = 2 }, icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "person")
        }, label = { Text(text = "Person") })
    }
}