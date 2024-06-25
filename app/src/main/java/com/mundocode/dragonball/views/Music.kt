package com.mundocode.dragonball.views

import MyBottomAppNavigation
import android.Manifest
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
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
            bottomBar = { MyBottomAppNavigation(navController = navController, selectedIndex = 2) }
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