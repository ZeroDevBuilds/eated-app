package com.zerodevbuilds.eated.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import com.zerodevbuilds.eated.ui.screen.batchdish.BatchDishScreen
import com.zerodevbuilds.eated.ui.screen.batchdish.BatchDishViewModel
import com.zerodevbuilds.eated.ui.screen.dishform.DishFormScreen
import com.zerodevbuilds.eated.ui.screen.dishform.DishFormViewModel
import com.zerodevbuilds.eated.ui.screen.restaurantform.RestaurantFormScreen
import com.zerodevbuilds.eated.ui.screen.restaurantform.RestaurantFormViewModel
import com.zerodevbuilds.eated.ui.screen.restaurantlist.RestaurantListScreen
import com.zerodevbuilds.eated.ui.screen.restaurantlist.RestaurantListViewModel

object Routes {
    const val RESTAURANT_LIST = "restaurant_list"
    const val ADD_RESTAURANT = "restaurant_form"
    const val EDIT_RESTAURANT = "restaurant_form/{restaurantId}"
    const val ADD_DISH = "dish_form/{restaurantId}"
    const val EDIT_DISH = "dish_form_edit/{dishId}"
    const val BATCH_DISH = "batch_dish/{restaurantId}"
}

@Composable
fun EatedNavigation(repository: RestaurantRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.RESTAURANT_LIST) {
        composable(Routes.RESTAURANT_LIST) {
            val vm: RestaurantListViewModel = viewModel(
                factory = RestaurantListViewModel.factory(repository)
            )
            RestaurantListScreen(
                viewModel = vm,
                onAddRestaurant = { navController.navigate(Routes.ADD_RESTAURANT) },
                onEditRestaurant = { id -> navController.navigate("restaurant_form/$id") },
                onAddDish = { restaurantId -> navController.navigate("dish_form/$restaurantId") },
                onBatchAddDish = { restaurantId -> navController.navigate("batch_dish/$restaurantId") },
                onEditDish = { dishId -> navController.navigate("dish_form_edit/$dishId") }
            )
        }

        composable(Routes.ADD_RESTAURANT) {
            val vm: RestaurantFormViewModel = viewModel(
                factory = RestaurantFormViewModel.factory(repository, null)
            )
            RestaurantFormScreen(viewModel = vm, onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.EDIT_RESTAURANT,
            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getLong("restaurantId")
            val vm: RestaurantFormViewModel = viewModel(
                factory = RestaurantFormViewModel.factory(repository, restaurantId)
            )
            RestaurantFormScreen(viewModel = vm, onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.ADD_DISH,
            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getLong("restaurantId")
            val vm: DishFormViewModel = viewModel(
                factory = DishFormViewModel.factory(repository, restaurantId, null)
            )
            DishFormScreen(viewModel = vm, onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.EDIT_DISH,
            arguments = listOf(navArgument("dishId") { type = NavType.LongType })
        ) { backStackEntry ->
            val dishId = backStackEntry.arguments?.getLong("dishId")
            val vm: DishFormViewModel = viewModel(
                factory = DishFormViewModel.factory(repository, null, dishId)
            )
            DishFormScreen(viewModel = vm, onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.BATCH_DISH,
            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getLong("restaurantId") ?: 0L
            val vm: BatchDishViewModel = viewModel(
                factory = BatchDishViewModel.factory(repository, restaurantId)
            )
            BatchDishScreen(viewModel = vm, onNavigateBack = { navController.popBackStack() })
        }
    }
}
