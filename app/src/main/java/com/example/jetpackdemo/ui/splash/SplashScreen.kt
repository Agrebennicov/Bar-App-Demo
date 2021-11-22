package com.example.jetpackdemo.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ui.theme.DrinkTheme


private enum class State { START, SERVE, CLINK, END }

/**
 * START - show one drink
 * SERVE - slide in the other 2 drinks
 * CLINK - clink the drinks
 * END - navigate to the next screen
 * */

private const val SERVE_ANIMATION_DURATION = 2000
private const val CLINK_ANIMATION_DURATION = 600
private const val FADE_OUT_ANIMATION_DURATION = 1000

private const val CLINK_ROTATION_ANGLE = 10f
private const val CLINK_X_OFFSET = 80
private const val CLINK_Y_OFFSET = -100

@ExperimentalAnimationApi
@Composable
fun SplashScreen(onDone: () -> Unit) {
    val animationTargetState = remember { mutableStateOf(State.START) }
    val transition = updateTransition(animationTargetState.value, "transition")

    val rotation = transition.animateFloat(
        targetValueByState = {
            when (it) {
                State.CLINK -> CLINK_ROTATION_ANGLE
                else -> 0f
            }
        },
        transitionSpec = { tween(durationMillis = CLINK_ANIMATION_DURATION) },
        label = "rotation"
    )

    val offset = transition.animateIntOffset(
        targetValueByState = {
            when (it) {
                State.CLINK -> IntOffset(x = CLINK_X_OFFSET, y = CLINK_Y_OFFSET)
                else -> IntOffset(x = 0, y = 0)
            }
        },
        transitionSpec = { tween(durationMillis = CLINK_ANIMATION_DURATION) },
        label = "offset",
    )

    val alpha = transition.animateFloat(
        targetValueByState = {
            when (it) {
                State.END -> 0f
                else -> 1f
            }
        },
        transitionSpec = { tween(durationMillis = FADE_OUT_ANIMATION_DURATION) },
        label = "fade out",
    )

    BarCounter(Modifier.alpha(alpha.value)) {
        Box(
            Modifier
                .weight(1f)
                .offset { offset.value }
                .rotate(rotation.value)
        ) {
            transition.AnimatedVisibility(
                visible = { it != State.START },
                enter = fadeIn() + slideInHorizontally(
                    animationSpec = tween(durationMillis = SERVE_ANIMATION_DURATION)
                ),
            ) {
                TequilaSunrise()
            }
        }

        Martini(modifier = Modifier.weight(1f))

        Box(
            Modifier
                .weight(1f)
                .offset { offset.value.copy(x = -offset.value.x) }
                .rotate(-rotation.value)
        ) {
            transition.AnimatedVisibility(
                visible = { it != State.START },
                enter = fadeIn() + slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = SERVE_ANIMATION_DURATION)
                ),
            ) {
                BloodyMary()
            }
        }
    }

    if (transition.currentState == transition.targetState) {
        when (animationTargetState.value) {
            State.START -> animationTargetState.value = State.SERVE
            State.SERVE -> animationTargetState.value = State.CLINK
            State.CLINK -> animationTargetState.value = State.END
            State.END -> LaunchedEffect(key1 = Unit, block = { onDone()})
        }
    }
}

@Composable
fun BarCounter(
    modifier: Modifier = Modifier,
    drinks: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .background(DrinkTheme.colors.primary)
            .fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .fillMaxHeight(0.3f)
                .padding(30.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            drinks(this)
        }
    }
}

@Composable
fun TequilaSunrise(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_tequila_sunrise),
        contentDescription = null,
    )
}

@Composable
fun Martini(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_martini),
        contentDescription = null,
    )
}

@Composable
fun BloodyMary(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_bloody_mary),
        contentDescription = null,
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ViewSplash() {
    SplashScreen {}
}