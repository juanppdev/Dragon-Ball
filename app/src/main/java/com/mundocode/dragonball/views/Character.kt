package com.mundocode.dragonball.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.transform.CircleCropTransformation
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.transformations
import com.mundocode.dragonball.viewmodels.MyViewModel
import com.mundocode.dragonball.viewmodels.MyViewModelFactory

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    id: Int
) {

    val viewModel = viewModel<MyViewModel> (
        factory = MyViewModelFactory(id)
    )

    // MARK: - State
    val dragonDetails by viewModel.dragonDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val gotError by viewModel.gotError.collectAsState()

    Content(
        isLoading = isLoading,
        gotError = gotError,
        dragonDetails = dragonDetails
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    gotError: Boolean,
    dragonDetails: SingleDragonBallLista?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp),
                color = Color.Blue,
                trackColor = Color.Red
            )
        } else if(gotError) {
            ErrorState()
        } else {
            dragonDetails.let { details ->
                if (details != null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(url = details.image)
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            text = details.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }

                }
            }
//            if (dragonDetails != null) {
//                AsyncImage(url = dragonDetails.originPlanet.image)
//            }
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
        modifier = Modifier.size(200.dp),
        painter = painter,
        contentDescription = null
    )
}