import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mundocode.dragonball.R
import com.mundocode.dragonball.components.Card
import com.mundocode.dragonball.components.MyTopAppBar
import com.mundocode.dragonball.viewmodels.DragonBallListViewModel
import com.mundocode.dragonball.views.ErrorState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InicioView(
    navController: NavController,
    viewModel: DragonBallListViewModel
) {
    val dragonList by viewModel.saiyanList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val scaffoldState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Scaffold(
            topBar = { MyTopAppBar(navController, showBackButton = false) },
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
            bottomBar = { MyBottomAppNavigation(navController, selectedIndex = 0) }
        ) {
            when {
                errorMessage != null -> {
                    ErrorState()
                }
                isLoading -> {
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
                }
                else -> {
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

                        dragonList?.let { list ->
                            val pagerState = rememberPagerState(pageCount = { list.ListItems.size })

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) { page ->
                                val item = list.ListItems.getOrNull(page)

                                item?.let {
                                    Card(
                                        it.name,
                                        it.image,
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
    }
}
