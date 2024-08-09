package com.example.f1livetiming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.f1livetiming.ui.F1LiveTimingNavHost
import com.example.f1livetiming.ui.theme.F1LiveTimingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            F1LiveTimingTheme {

                val navController = rememberNavController()
                val items = listOf(Screens.LiveTimingScreen, Screens.RadiosScreen)

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {

                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            
                            items.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any {it.route == screen.route } == true,
                                    label = { Text(text = stringResource(id = screen.resourceId))},
                                    icon = { Icon(painter = painterResource(id = screen.drawableId), contentDescription = "")},
                                    onClick = {
                                        navController.navigate(screen.route){
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    })
                            }

                        }
                    }) { innerPadding ->

                    F1LiveTimingNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )

                }
            }
        }
    }
}
