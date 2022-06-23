package com.tencent.igs.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tencent.igs.ui.game.menu.MenuTextButton

@Composable
fun ScoreScreen(navController: NavController, score: Int) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_4),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.DarkGray
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Game over!",
                        fontSize = 58.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Your score: $score",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    MenuTextButton(text = "Play again?") {
                        navigateToGame(navController)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuTextButton(text = "Menu") {
                        navigateToMenu(navController)
                    }
                }
            }
        }
    }
}

fun navigateToMenu(navController: NavController) {
    navController.navigate("menu") {
        popUpTo("menu") { inclusive = true }
    }
}

fun navigateToGame(navController: NavController) {
    navController.navigate("game") {
        popUpTo("menu") { inclusive = true }
    }
}