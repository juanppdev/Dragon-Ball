package com.mundocode.dragonball.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mundocode.dragonball.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Music(
    navController: NavController,
    context: Context
) {
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
            bottomBar = { MyBottomNavigationMusic(navController) }
        ) { it ->

            val soundResources = listOf(
                R.raw.mrdream,
                R.raw.makafushigi,
                R.raw.son_gokus_song,
                R.raw.wolf_hurricane,
                R.raw.yume_wo_oshite,
                R.raw.aoki_tabibito_tachi,
                R.raw.dragonball_densetsu,
                R.raw.fushigi_wonderland,
                R.raw.goku_no_gokihen_journey,
                R.raw.hatsukoi_wa_kumo_ni_notte,
                R.raw.kaze_wo_kanjite,
                R.raw.moeru_heart_de_red_ribbon_gun_wo_yattsukero,
                R.raw.muten_roshi_no_oshie,
                R.raw.red_ribbon_army,
                R.raw.romantic_ageru_yo,
                R.raw.mezase_tenka_ichi
            )

            //val mediaPlayers = soundResources.map { MediaPlayer.create(context, it) }
            val mediaPlayers = remember {
                soundResources.map { MediaPlayer.create(context, it) }
            }

            val permissionState = rememberPermissionState(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner) {
                onDispose {
                    mediaPlayers.forEach { it.release() } // Liberar todos los MediaPlayer
                }
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        permissionState.launchPermissionRequest()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            LazyColumn(modifier = Modifier.padding(it)) {
                items(mediaPlayers) { mediaPlayer ->
                    Row {

                        var isPlaying by remember { mutableStateOf(false) }

                        Text(text = "${(mediaPlayer.duration / 1000) / 60}:${(mediaPlayer.duration / 1000)  % 60 }", color = Color.White)
                        IconButton(onClick = { isPlaying = !isPlaying
                            if (isPlaying) {
                                mediaPlayer.start()
                            } else {
                                mediaPlayer.pause()
                            } }) {
                            Icon(
                                painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                                contentDescription = "Play",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomNavigationMusic(navController: NavController) {
    var index by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(containerColor = Color(0xFF228B22)) {
        NavigationBarItem(
            selected = index == 0,
            onClick = { navController.navigate("characters") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Personajes",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22))
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = { navController.navigate("planets") },
            icon = {
                Icon(
                    painterResource(id = R.drawable.planet), contentDescription = "Planetas",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22))
        )
        NavigationBarItem(selected = index == 2, onClick = { index = 2 }, icon = {
            Icon(
                imageVector = Icons.Default.PlayArrow, contentDescription = "music",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF228B22)))
    }
}