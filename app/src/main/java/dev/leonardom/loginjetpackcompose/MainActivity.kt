package dev.leonardom.loginjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.leonardom.loginjetpackcompose.navigation.Destinations
import dev.leonardom.loginjetpackcompose.presentation.home.HomeScreen
import dev.leonardom.loginjetpackcompose.presentation.login.LoginScreen
import dev.leonardom.loginjetpackcompose.presentation.login.LoginViewModel
import dev.leonardom.loginjetpackcompose.presentation.registration.RegisterViewModel
import dev.leonardom.loginjetpackcompose.presentation.registration.RegistrationScreen
import dev.leonardom.loginjetpackcompose.ui.theme.LoginJetpackComposeTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginJetpackComposeTheme {
                val navController = rememberAnimatedNavController()

                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Destinations.Login.route
                    ){
                        addLogin(navController)

                        addRegister(navController)

                        addHome()
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addLogin(
    navController: NavHostController
){
    composable(
        route = Destinations.Login.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {_,_ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }
    ){
        val viewModel: LoginViewModel = hiltViewModel()
        val email = viewModel.state.value.email
        val password = viewModel.state.value.password

        if(viewModel.state.value.successLogin){
            LaunchedEffect(key1 = Unit){
                navController.navigate(
                    Destinations.Home.route + "/$email" + "/$password"
                ){
                    popUpTo(Destinations.Login.route){
                        inclusive = true
                    }
                }
            }
        } else {
            LoginScreen(
                state = viewModel.state.value,
                onLogin = viewModel::login,
                onNavigateToRegister = {
                    navController.navigate(Destinations.Register.route)
                },
                onDismissDialog = viewModel::hideErrorDialog
            )
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addRegister(
    navController: NavHostController
){
    composable(
        route = Destinations.Register.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {_,_ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }
    ){
        val viewModel: RegisterViewModel = hiltViewModel()

        RegistrationScreen(
            state = viewModel.state.value,
            onRegister = viewModel::register,
            onBack = {
                navController.popBackStack()
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addHome() {
    composable(
        route = Destinations.Home.route + "/{email}" + "/{password}",
        arguments = Destinations.Home.arguments
    ){ backStackEntry ->

        val email = backStackEntry.arguments?.getString("email") ?: ""
        val password = backStackEntry.arguments?.getString("password") ?: ""

        HomeScreen(email, password)
    }
}














