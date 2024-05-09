package com.mundocode.dragonball.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.mutableStateOf
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
import com.mundocode.dragonball.models.DragonBallModel
import com.mundocode.dragonball.models.SingleDragonBallLista

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
                            snackbarData = data,
                            containerColor = Color.LightGray,
                            contentColor = Color.Blue
                        )
                    }
                )
            },
            bottomBar = { MyBottomNavigation() }
        ) { PaddingValues ->
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

                    val pager = dragonList?.ListItems?.size

                    val pagerState = rememberPagerState(pageCount = { pager ?: 0 })

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                    ) {
                        ListaLazyRow(navController = navController, dragonList = dragonList)
                    }
                }
            }
        }
    }
}

@Composable
fun ListaLazyRow(
    viewModel: PokemonListViewModel = viewModel(),
    navController: NavController,
    dragonList: DragonBallModel?
) {
    dragonList.let { detail ->
        detail?.ListItems?.forEach {
            Card(
                it.name,
                it.image,
                navController,
                modifier = Modifier
                    .clickable { navController.navigate("characters/${it.id}") }
            )
        }

//
//    LazyRow(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//
//        pokemonList.value?.let { results ->
//            items(results.ListItems.size) {
//                val result = results.ListItems[it]
//                Card(
//                    result.name,
//                    result.image,
//                    navController,
//                    modifier = Modifier
//                        .clickable { navController.navigate("characters/${result.id}") }
//                )
//            }
//        }
//
////        pokemonList.value?.ListItems?.forEach {
////
////            Card(
////                it.name,
////                it.image,
////                navController,
////                modifier = Modifier
////                    .clickable { navController.navigate("characters/${it.id}") }
////            )
////        }
//    }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar() {
    TopAppBar(
        title = { Text(text = "Mi primera Toolbar") },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            containerColor = Color.Red
        ),
    )
}


@Composable
fun MyBottomNavigation() {
    var index by remember {
        mutableStateOf(0)
    }
    NavigationBar(contentColor = Color.White, containerColor = Color.Red) {
        NavigationBarItem(selected = index == 0, onClick = { index = 0 }, icon = {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        }, label = { Text(text = "Home") })
        NavigationBarItem(selected = index == 1, onClick = { index = 1 }, icon = {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "fav")
        }, label = { Text(text = "FAV") })
        NavigationBarItem(selected = index == 2, onClick = { index = 2 }, icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "person")
        }, label = { Text(text = "Person") })
    }
}