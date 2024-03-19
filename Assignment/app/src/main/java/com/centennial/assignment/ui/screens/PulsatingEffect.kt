package com.centennial.assignment.ui.screens

import ScreenNames
import androidx.compose.animation.core.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun GoBackButtonPreview() {
    val navController = rememberNavController()
    GoBackButton(navController = navController)
}

@Composable
fun GoBackButton(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                navController.navigate(ScreenNames.HOME)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(Strings.GO_BACK_TO_HOME_SCREEN)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PulsatingHeart() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.0f at 0 with LinearOutSlowInEasing 
                0.2f at 250 with FastOutLinearInEasing 
                0.2f at 750 
                1.0f at 1000 
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val interactionSource = remember { MutableInteractionSource() }

    if (scale == 1.0f) {
        LaunchedEffect(Unit) {
            interactionSource.emit(PressInteraction.Press(Offset.Zero))
            delay(200)
            interactionSource.emit(PressInteraction.Release(PressInteraction.Press(Offset.Zero)))
        }
    }

    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.LightGray, Color.Magenta, Color.White)
    val color by remember(scale) {
        derivedStateOf {
            val fractionalIndex = (scale * (colors.size - 1)).coerceIn(0f, (colors.size - 1).toFloat())
            val startIndex = fractionalIndex.toInt()
            val endIndex = startIndex + 1
            val fraction = fractionalIndex - startIndex
            if (endIndex < colors.size) {
                lerpColors(colors[startIndex], colors[endIndex], fraction)
            } else {
                colors[startIndex]
            }
        }
    }

    Icon(
        imageVector =  Icons.Default.Favorite,
        contentDescription = "",
        tint = color,
        modifier = Modifier
            .size(50.dp * scale)
            .offset(x = 10.dp, y = 10.dp)
            .clickable(
                indication = rememberRipple(bounded = false),
                interactionSource = interactionSource
            ) { /* Handle click event if needed */ }
    )
}

fun lerpColors(startColor: Color, endColor: Color, fraction: Float): Color {
    return Color(
        red = lerp(startColor.red, endColor.red, fraction),
        green = lerp(startColor.green, endColor.green, fraction),
        blue = lerp(startColor.blue, endColor.blue, fraction),
        alpha = lerp(startColor.alpha, endColor.alpha, fraction)
    )
}

fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
    return (1 - fraction) * startValue + fraction * endValue
}


