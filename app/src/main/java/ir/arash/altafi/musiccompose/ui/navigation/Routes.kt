package ir.arash.altafi.musiccompose.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    val route: String

    @Serializable
    data object Splash : Route {
        override val route: String = ".ui.navigation.Route.Splash"
    }

    @Serializable
    data object Main : Route {
        override val route: String = ".ui.navigation.Route.Main"
    }

    @Serializable
    data object Main2 : Route {
        override val route: String = ".ui.navigation.Route.Main2"
    }

    @Serializable
    data object User : Route {
        override val route: String = ".ui.navigation.Route.User"
    }

    @Serializable
    data object Celebrity : Route {
        override val route: String = ".ui.navigation.Route.Celebrity"
    }

    @Serializable
    data object Paging : Route {
        override val route: String = ".ui.navigation.Route.Paging"
    }

    @Serializable
    data class ImageScreen(var title: String, val imageUrl: String) : Route {
        override val route: String = ".ui.navigation.Route.ImageScreen"
    }

    @Serializable
    data object TestList : Route {
        override val route: String = ".ui.navigation.Route.TestList"
    }

    @Serializable
    data object TestPagingList : Route {
        override val route: String = ".ui.navigation.Route.TestPagingList"
    }

    @Serializable
    data class TestDetail(var userId: String) : Route {
        override val route: String = ".ui.navigation.Route.TestDetail"
    }



    @Serializable
    data object Login : Route {
        override val route: String = ".ui.navigation.Route.Login"
    }

    @Serializable
    data object Register : Route {
        override val route: String = ".ui.navigation.Route.Register"
    }

    @Serializable
    data object Logout : Route {
        override val route: String = ".ui.navigation.Route.Logout"
    }

    @Serializable
    data object Home : Route {
        override val route: String = ".ui.navigation.Route.Home"
    }

    @Serializable
    data object Profile : Route {
        override val route: String = ".ui.navigation.Route.Profile"
    }

    @Serializable
    data object MusicVideo : Route {
        override val route: String = ".ui.navigation.Route.MusicVideo"
    }

    @Serializable
    data object Music : Route {
        override val route: String = ".ui.navigation.Route.Music"
    }
}