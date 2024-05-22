package com.mundocode.dragonball.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mundocode.dragonball.components.MyTabScreen
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.viewmodels.MyViewModel
import com.mundocode.dragonball.viewmodels.MyViewModelFactory

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    id: Int
) {

    val viewModel = viewModel<MyViewModel>(
        factory = MyViewModelFactory(id)
    )

    // MARK: - State
    val dragonDetails by viewModel.dragonDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val gotError by viewModel.gotError.collectAsState()

    Content(
        isLoading = isLoading,
        gotError = gotError,
        dragonDetails = dragonDetails,
        navController = navController
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    gotError: Boolean,
    dragonDetails: SingleDragonBallLista?,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp),
                color = Color.Blue,
                trackColor = Color.Red
            )
        } else if (gotError) {
            ErrorState()
        } else {

            val scaffoldState = remember { SnackbarHostState() }

            Scaffold(
                containerColor = Color.Black,
                topBar = {
                    MyTopAppBarCharacter(navController)
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = scaffoldState,
                        snackbar = { data ->
                            Snackbar(
                                snackbarData = data
                            )
                        }
                    )
                },
                bottomBar = { MyBottomNavigation(navController) }
            ) {

                dragonDetails.let { details ->
                    if (details != null) {

                        LazyColumn(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            item {

                                Box(
                                    modifier = Modifier
                                        .background(Color.Gray)
                                        .fillMaxWidth()
                                        .height(300.dp), contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(url = details.image, modifier = Modifier)
                                }
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = "Max. Power Level",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = details.maxKi,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = "Descripcion",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = details.description,
                                    textAlign = TextAlign.Start,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = "Planeta al que pertence",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                AsyncImage(url = details.originPlanet.image, modifier = Modifier.fillMaxWidth().height(250.dp).border(2.dp, Color.Red))

                                details.transformations.forEach {
                                    MyTabScreen()
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
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBarCharacter(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Dragon Ball") },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            containerColor = Color(0xFF228B22)
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

    )
}

@Composable
private fun AsyncImage(url: String, modifier: Modifier) {
    val painter: Painter = // Optionally, you can apply transformations
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // Optionally, you can apply transformations
                    transformations()
                }).build()
        )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = null
    )
}