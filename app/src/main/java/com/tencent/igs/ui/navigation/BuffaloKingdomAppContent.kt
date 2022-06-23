package com.tencent.igs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tencent.igs.ui.game.GameScreen
import com.tencent.igs.ui.game.GameViewModel
import com.tencent.igs.ui.game.ScoreScreen
import com.tencent.igs.ui.game.menu.MenuScreen
import com.tencent.igs.ui.game.rules.RuleScreen

@Composable
fun BuffaloKingdomAppContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "init"
    ) {

        composable("menu") {
            MenuScreen(navController)
        }

        composable("game") {
            val viewModel: GameViewModel = hiltViewModel()
            GameScreen(navController, viewModel)
        }

        composable("rule") {
            RuleScreen(navController)
        }



        composable(
            "score/{score}", arguments = listOf(
                navArgument("score") {
                    type = NavType.IntType
                    defaultValue = 0
                })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("score")?.let { score ->
                ScoreScreen(
                    navController,
                    score
                )
            }
        }
    }
}
