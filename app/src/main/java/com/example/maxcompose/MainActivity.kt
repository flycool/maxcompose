package com.example.maxcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.*
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.NavGraphs
import com.example.maxcompose.model.*
import com.example.maxcompose.ui.theme.MaxcomposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo
import kotlin.math.*

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaxcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    Scaffold(bottomBar = {
                        BottomBar(navController = navController)
                    }) {

                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                icon = {},
                label = { Text(text = destination.label) },
                selected = currentDestination?.hierarchy?.any { it.label == destination.label } == true,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun Compose1Home(navigator: DestinationsNavigator) {
    LazyColumn() {
        items(composeItemList) { item ->
            Text(text = item.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(item.destination)
                    })
        }
    }
}

@Destination
@Composable
fun Compose2Home(navigator: DestinationsNavigator) {
    LazyColumn() {
        items(composeItemList2) { item ->
            Text(text = item.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(item.destination)
                    })
        }
    }
}




















