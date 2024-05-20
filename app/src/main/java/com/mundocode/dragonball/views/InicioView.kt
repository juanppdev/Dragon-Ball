package com.mundocode.dragonball.views

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mundocode.dragonball.viewmodels.PokemonListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mundocode.dragonball.R
import com.mundocode.dragonball.components.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.pager.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InicioView(
    navController: NavController,
    viewModel: PokemonListViewModel = viewModel()
) {
    val dragonList by viewModel.dragonList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    LaunchedEffect(dragonList) {
        viewModel.getPokemonList()
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
                            snackbarData = data
                        )
                    }
                )
            },
            bottomBar = { MyBottomNavigation(navController) }
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

                    val pagerState = rememberPagerState(pageCount = { 58 })

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize().padding(it)
                    ) { page ->
                        // Accede al elemento correspondiente en dragonList.ListItems
                        val item = dragonList?.ListItems?.getOrNull(page)

                        // Verifica si el elemento no es nulo antes de crear el Text
                        item?.let {
                            Card(
                                it.name,
                                it.image,
                                navController,
                                modifier = Modifier
                                    .clickable { navController.navigate("characters/${it.id}") }
                                    .padding(32.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar() {
    val navController = rememberNavController()
    TopAppBar(
        title = { Text(text = "Dragon Ball") },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            containerColor = Color(0xFF228B22)
        )
    )
}


@Composable
fun MyBottomNavigation(navController: NavController) {
    var index by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(containerColor = Color(0xFF228B22)) {
        NavigationBarItem(selected = index == 0, onClick = { index = 0 }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Personajes",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22)))
        NavigationBarItem(
            selected = index == 1,
            onClick = { navController.navigate("planets") },
            icon = {
                Icon(
                    painterResource(id = R.drawable.planet), contentDescription = "Planetas",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22)))
        NavigationBarItem(selected = index == 2, onClick = { navController.navigate("music") }, icon = {
            Icon(
                imageVector = Icons.Default.PlayArrow, contentDescription = "music",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22)))
    }
}