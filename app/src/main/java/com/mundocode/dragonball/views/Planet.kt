import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
                    Snackbar(
                        snackbarData = data
                    )
<<<<<<< HEAD
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

                                AsyncImage(url = details.image, modifier = Modifier.fillMaxWidth().height(350
                                    .dp))

                                Text(
                                    text = details.name,
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(text = details.description, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(vertical = 20.dp))

                                details.characters.forEach {
                                    Text(
                                        text = it.name,
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }

                    }
=======
>>>>>>> bottombarchange
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
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = details.name,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    details.characters.forEach {
                        Text(
                            text = it.name,
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
