package com.own.bogdanpremium.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.own.bogdanpremium.screens.appreciation.AppreciationScreen
import com.own.bogdanpremium.screens.datescience.DateScienceScreen
import com.own.bogdanpremium.screens.nameverify.NameVerifyScreen
import com.own.bogdanpremium.screens.subscribe.SubscribeScreen
import com.own.bogdanpremium.screens.surprisevideo.SurpriseVideoScreen
import com.own.bogdanpremium.screens.tinder.TinderScreen
import com.own.bogdanpremium.screens.welcome.WelcomeScreen

/**
 * Single navigation host for the whole app. The flow is forward-only:
 * welcome -> nameVerify -> appreciation -> surpriseVideo -> tinder -> dateScience -> subscribe.
 */
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onJumpIn = { navController.navigate(Routes.NAME_VERIFY) })
        }
        composable(Routes.NAME_VERIFY) {
            NameVerifyScreen(onContinue = { navController.navigate(Routes.APPRECIATION) })
        }
        composable(Routes.APPRECIATION) {
            AppreciationScreen(onFinished = { navController.navigate(Routes.SURPRISE_VIDEO) })
        }
        composable(Routes.SURPRISE_VIDEO) {
            SurpriseVideoScreen(onNext = { navController.navigate(Routes.TINDER) })
        }
        composable(Routes.TINDER) {
            TinderScreen(onLiked = { navController.navigate(Routes.DATE_SCIENCE) })
        }
        composable(Routes.DATE_SCIENCE) {
            DateScienceScreen(onNext = { navController.navigate(Routes.SUBSCRIBE) })
        }
        composable(Routes.SUBSCRIBE) {
            SubscribeScreen()
        }
    }
}
