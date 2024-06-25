package com.mundocode.dragonball.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mundocode.dragonball.models.transformations

@Composable
fun MyTabScreen(tabs: List<transformations>) {

    var tabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier,
            containerColor = Color.DarkGray,
            contentColor = Color.Yellow,
            indicator = { tabPositions ->
                TabRowDefaults.apply {
                    Divider(
                        Modifier
                            .height(2.dp)
                            .padding(horizontal = 16.dp)
                            .tabIndicatorOffset(tabPositions[tabIndex]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            divider ={}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title.name) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        when (tabIndex) {
            in 0..5 -> HomeScreen(tabs[tabIndex].image)
        }
    }
}

@Composable
fun HomeScreen(image: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(url = image, modifier = Modifier
            .fillMaxWidth()
            .height(450.dp))
    }
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