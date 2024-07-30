package com.habitsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.habitsapp.authentication.presentation.login.LoginScreen
import com.habitsapp.authentication.presentation.singup.SingUpScreen
import com.habitsapp.home.presentation.detail.DetailScreen
import com.habitsapp.home.presentation.home.HomeScreen
import com.habitsapp.onboarding.presentation.OnboardingScreen
import com.habitsapp.settings.presentation.SettingsScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute,
    logout: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = startDestination.route) {
        composable(NavigationRoute.Onboarding.route) {
            OnboardingScreen(onFinish = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Login.route)
            })
        }

        composable(NavigationRoute.Login.route) {
            LoginScreen(
                onLogin = {
                    navHostController.popBackStack()
                    navHostController.navigate(NavigationRoute.Home.route)
                },
                onSignUp = {
                    navHostController.navigate(NavigationRoute.SignUp.route)
                }
            )
        }

        composable(NavigationRoute.SignUp.route) {
            SingUpScreen(
                onSignIn = {
                    navHostController.navigate(NavigationRoute.Login.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onLogin = {
                    navHostController.navigate(NavigationRoute.Home.route)
                }

            )
        }

        composable(NavigationRoute.Home.route) {
            HomeScreen(
                onNewHabit = {
                    navHostController.navigate(NavigationRoute.Detail.route)
                },
                onSettings = {
                    navHostController.navigate(NavigationRoute.Settings.route)
                },
                onEditHabit = {
                    navHostController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
                }
            )
        }

        composable(NavigationRoute.Detail.route + "?habitId={habitId}", arguments = listOf(
            navArgument("habitId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )) {
            DetailScreen(
                onBack = {
                    navHostController.popBackStack()
                },
                onSave = { navHostController.popBackStack() })
        }

        composable(NavigationRoute.Settings.route) {
            SettingsScreen(
                onBack = {
                    navHostController.popBackStack()
                },
                onLogout = {
                    logout()
                    navHostController.navigate(NavigationRoute.Login.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
