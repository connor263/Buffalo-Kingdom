package com.tencent.igs.ui.game.rules

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import com.tencent.igs.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tencent.igs.ui.game.composables.RuleView
import kotlinx.coroutines.launch

@Composable
fun RuleScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    var entered by remember { mutableStateOf(false) }
    var indexAnim by remember { mutableStateOf(0) }
    val fallingAnimation = remember { Animatable(-800F) }
    val alphaAnimation = remember { Animatable(1F) }

    Image(
        painter = painterResource(id = R.drawable.background_3),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RuleView(
                modifier = Modifier
                    .absoluteOffset(y = fallingAnimation.value.dp)
                    .weight(0.35F)
                    .alpha(alphaAnimation.value),
                text = "You can only move the pieces in specific ways",
                drawableId = R.drawable.decal_1
            )

            RuleView(
                modifier = Modifier
                    .absoluteOffset(y = fallingAnimation.value.dp + 4.dp)
                    .weight(0.35F)
                    .alpha(alphaAnimation.value),
                text = "Your game is over if your pieces reach the top of the screen",
                drawableId = R.drawable.decal_2
            )

            RuleView(
                modifier = Modifier
                    .absoluteOffset(y = fallingAnimation.value.dp + 4.dp)
                    .weight(0.35F)
                    .alpha(alphaAnimation.value),
                text = "You can only remove pieces from the screen by filling all the blank space in a line",
                drawableId = R.drawable.decal_3
            )
        }
    }

    LaunchedEffect(entered) {
        indexAnim++
    }

    LaunchedEffect(indexAnim) {
        fallingAnimation.animateTo(
            targetValue = fallingAnimation.value + 50F,
            animationSpec = tween(durationMillis = 100)
        ).apply {
            when (this.endReason) {
                AnimationEndReason.BoundReached -> {}
                AnimationEndReason.Finished -> {
                    if (fallingAnimation.value + 50F <= 0) {
                        indexAnim++
                    }
                }
            }
        }
        entered = true
    }

    BackHandler {
        scope.launch {
            alphaAnimation.animateTo(
                0F, animationSpec = tween(400)
            ).apply {
                when (endReason) {
                    AnimationEndReason.BoundReached -> {}
                    AnimationEndReason.Finished -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}