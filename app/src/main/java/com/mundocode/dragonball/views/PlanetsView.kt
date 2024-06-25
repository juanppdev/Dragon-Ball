package com.mundocode.dragonball.views

import MyBottomAppNavigation
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mundocode.dragonball.R
import com.mundocode.dragonball.components.MyTopAppBar
import com.mundocode.dragonball.viewmodels.DragonBallListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetsView(
    navController: NavController,
    viewModel: DragonBallListViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val planetsList by viewModel.planetList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPlanets()
    }

    val scaffoldState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { MyTopAppBar(navController = navController, showBackButton = false) },
            containerColor = Color.Black,
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
            bottomBar = { MyBottomAppNavigation(navController = navController, selectedIndex = 1) }
        ) {
            if (errorMessage != null) {
                ErrorState()
            } else if (isLoading) {
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

                    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.padding(it)) {
                        planetsList?.ListPlanets?.let { list ->
                            items(list.size) { index ->
                                val planet = list[index]
                                Card(
                                    onClick = { navController.navigate("planets/${planet.id}") },
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable { navController.navigate("planets/${planet.id}") }
                                            .padding(16.dp)
                                    ) {
                                        AsyncImage(url = planet.image)
                                        Column(
                                            modifier = Modifier.padding(start = 16.dp)
                                        ) {
                                            Text(
                                                text = planet.name,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = planet.description,
                                                color = Color.White,
                                                maxLines = 3,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(top = 8.dp)
                                            )
                                        }
                                    }
                                }
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
    val painter: Painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                transformations()
            }).build()
    )

    Image(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .padding(vertical = 15.dp),
        painter = painter,
        contentDescription = null
    )
}
