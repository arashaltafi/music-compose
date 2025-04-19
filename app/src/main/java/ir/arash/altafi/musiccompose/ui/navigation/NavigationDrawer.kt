package ir.arash.altafi.musiccompose.ui.navigation

import ir.arash.altafi.musiccompose.R

data class NavigationDrawerItem(
    val label: Int,
    val icon: Int,
    val route: Route,
)

fun navigationDrawerItems(): List<NavigationDrawerItem> {
    return listOf(
        NavigationDrawerItem(
            label = R.string.home,
            icon = R.drawable.home,
            route = Route.Home,
        ),
        NavigationDrawerItem(
            label = R.string.music,
            icon = R.drawable.music,
            route = Route.Music,
        ),
        NavigationDrawerItem(
            label = R.string.music_video,
            icon = R.drawable.music_video,
            route = Route.MusicVideo,
        ),
        NavigationDrawerItem(
            label = R.string.profile,
            icon = R.drawable.profile,
            route = Route.Profile,
        ),
    )
}