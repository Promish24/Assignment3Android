package com.centennial.assignment.ui.screens

import Strings
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.centennial.assignment.R


@Preview(showBackground = true)
@Composable
fun ScaleButton() {
    var buttonToggled by remember {
        mutableStateOf(false)
    }

    val buttonScale by animateFloatAsState(
        targetValue = if (buttonToggled) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val textScale by animateFloatAsState(
        targetValue = if (buttonToggled) 0.8f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val textSize by animateFloatAsState(
        targetValue = if (buttonToggled) 20.sp.value else 16.sp.value,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Text(Strings.CLICK_TO_SCALE, fontSize = 20.sp)

    Button(
        onClick = { buttonToggled = !buttonToggled },
        modifier = Modifier.scale(buttonScale),
    ) {
        Text(
            text = if (buttonToggled) Strings.SCALE_DOWN else Strings.SCALE_UP,
            modifier = Modifier
                .scale(textScale)
                .align(Alignment.CenterVertically),
            fontSize = textSize.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RocketImage() {
    var imageToggled by remember {
        mutableStateOf(false)
    }

    val imageRotation by animateFloatAsState(
        targetValue = if (imageToggled) 360f else -360f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rocket_image),
            contentDescription = Strings.ROCKET_IMAGE,
            modifier = Modifier

                .rotate(imageRotation)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { imageToggled = !imageToggled }
                )
        )
        Text(Strings.CLICK_TO_ROTATE, fontSize = 20.sp, color = Color.Black)
    }
}

@Composable
fun PulsatingButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate(ScreenNames.PULSING)
        },
    ) {
        Text(Strings.CHECK_PULSATING_ANIMATION)
    }
}
