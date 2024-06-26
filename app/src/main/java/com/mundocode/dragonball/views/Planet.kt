import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mundocode.dragonball.components.MyTopAppBar
import com.mundocode.dragonball.models.singlePlanets
import com.mundocode.dragonball.viewmodels.MyViewModel
import com.mundocode.dragonball.viewmodels.MyViewModelFactory


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlanetDetailsScreen(
    navController: NavController,
    id: Int
) {
    val viewModel = viewModel<MyViewModel>(
        factory = MyViewModelFactory(id)
    )
    val dragonDetails by viewModel.planetDetails.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { MyTopAppBar(navController = navController, showBackButton = true) },
        bottomBar = { MyBottomAppNavigation(navController = navController, selectedIndex = 1) },
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState,
                snackbar = { data ->
                    Snackbar(snackbarData = data)
                }
            )
        },
        containerColor = Color.Black
    ) {
        ContentPlanet(
            dragonDetails = dragonDetails,
            paddingValues = it
        )
    }
}

@Composable
private fun ContentPlanet(
    dragonDetails: singlePlanets?,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        dragonDetails?.let { details ->
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    AsyncImage(
                        model = details.image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )

                    Text(
                        text = details.name,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = details.description,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )

                    details.characters.forEach { character ->
                        Text(
                            text = character.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
