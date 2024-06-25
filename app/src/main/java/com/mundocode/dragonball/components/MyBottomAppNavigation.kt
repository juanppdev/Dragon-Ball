import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mundocode.dragonball.R

@Composable
fun MyBottomAppNavigation(navController: NavController, selectedIndex: Int) {
    NavigationBar(containerColor = Color(0xFF228B22)) {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = {
                if (selectedIndex != 0) {
                    navController.navigate("characters") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.personajes),
                    contentDescription = "Personajes",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF228B22), // Color de fondo igual al BottomBar
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            )
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {
                if (selectedIndex != 1) {
                    navController.navigate("planets") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.planet_kaiosama),
                    contentDescription = "Planetas",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF228B22), // Color de fondo igual al BottomBar
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            )
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = {
                if (selectedIndex != 2) {
                    navController.navigate("music") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.dbz_music),
                    contentDescription = "Music",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF228B22), // Color de fondo igual al BottomBar
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            )
        )
    }
}
