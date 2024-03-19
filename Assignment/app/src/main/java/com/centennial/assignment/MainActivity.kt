package com.centennial.assignment

import ScreenNames
import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.centennial.assignment.ui.screens.GoBackButton
import com.centennial.assignment.ui.screens.PulsatingButton
import com.centennial.assignment.ui.screens.PulsatingHeart
import com.centennial.assignment.ui.screens.RocketAnimation
import com.centennial.assignment.ui.screens.RocketImage
import com.centennial.assignment.ui.screens.ScaleButton
import com.centennial.assignment.ui.theme.AssignmentTheme

const val time = 1500

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO

        setContent {
            AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "HomeScreen",
                        enterTransition = {
                            fadeIn(animationSpec = tween(time)) + slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left, tween(time)
                            )
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(time)) + slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down, tween(time)
                            )
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(time)) + slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up, tween(time)
                            )
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(time)) + slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right, tween(time)
                            )
                        }
                    ) {
                        composable(route = ScreenNames.HOME) {
                            RocketAnimation(navController = navController)
                        }
                        composable(route = ScreenNames.DETAILS) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                RocketImage()
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ScaleButton()
                                Spacer(modifier = Modifier.width(16.dp))
                                PulsatingButton(
                                    navController = navController
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                        composable(route = ScreenNames.PULSING) {
                            PulsatingHeart()
                            GoBackButton(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
