package com.centennial.assignment.ui.screens

import Strings
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centennial.assignment.R
import com.centennial.assignment.ui.theme.AssignmentTheme
import kotlin.system.exitProcess

@Composable
fun RocketAnimation(navController: NavController) {
    var launchRocket by remember { mutableStateOf(false) }
    var landRocket by remember { mutableStateOf(false) }

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(onBack = {
        showExitDialog = true
    })

    if (showExitDialog) {
        AlertDialog(
            title = { Text(Strings.EXIT_APP) },
            text = { Text(Strings.DO_YOU_WANT_TO_EXIT) },
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    exitProcess(0)
                }) {
                    Text(Strings.YES)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(Strings.NO)
                }
            }
        )
    }

    val targetValue = when {
        launchRocket -> (-500).dp
        landRocket -> 0.dp
        else -> 0.dp
    }
    val position by animateDpAsState(
        targetValue = targetValue,
        animationSpec = tween(1000), label = ""
    )

    val rotation by animateFloatAsState(
        targetValue = if (launchRocket) 180f else 0f,
        animationSpec = tween(1000), label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.rocket_image),
            contentDescription = Strings.ROCKET_IMAGE,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(150.dp)
                .offset(y = position)
                .rotate(rotation)
        )

        AnimatedVisibility(
            visible = position == targetValue && launchRocket,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Button(
                onClick = { navController.navigate(ScreenNames.DETAILS) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(Strings.SEE_MORE_ANIMATIONS)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    launchRocket = true
                    landRocket = false
                },
                modifier = Modifier.weight(1f),
            ) {
                Text(Strings.LAUNCH_ROCKET)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    landRocket = true
                    launchRocket = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(Strings.LAND_ROCKET)
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RocketAnimationPreview() {
    AssignmentTheme {
        val navController = rememberNavController()
        RocketAnimation(navController)
    }
}
