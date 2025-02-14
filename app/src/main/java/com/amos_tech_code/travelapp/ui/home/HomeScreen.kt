package com.amos_tech_code.travelapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amos_tech_code.travelapp.cache.TravelAppSession
import com.amos_tech_code.travelapp.navigation.NavRoutes
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

@SuppressLint("CoroutineCreationDuringComposition", "ProduceStateDoesNotAssignValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController : NavController,
    session: TravelAppSession
) {

    val coroutineScope = rememberCoroutineScope()
    var userName by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        userName = session.getUserName() ?: "Guest"
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        TopAppBar(
            title = {
                Text("Explore the World, $userName")
                    },
            actions = {
                IconButton(
                    onClick = {
                    coroutineScope.launch {
                        session.clearSession()
                    }
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Home.route) {
                            inclusive = true
                        }
                    }
                }
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Log out", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        // Search Bar
        SearchBar()

        // Featured Destinations
        FeaturedDestinations()

        // Popular Destinations
        PopularDestinations()
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        label = { Text("Search for destinations...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
    )
}

@Composable
fun FeaturedDestinations() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Featured Destinations",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) { index ->
                DestinationCard(destination = "Destination $index")
            }
        }
    }
}

@Composable
fun PopularDestinations() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Popular Destinations",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                DestinationCard(destination = "Popular Destination $index")
            }
        }
    }
}

@Composable
fun DestinationCard(destination: String) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            Text(
                destination,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                color = Color.White
            )
        }
    }
}
