package ir.arash.altafi.musiccompose.ui.navigation

import ir.arash.altafi.musiccompose.R

data class BottomNavigationItem(
    val label: Int,
    val icon: Int,
    val route: Route,
    val badgeCount: Int,
)

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = R.string.home,
            icon = R.drawable.home,
            route = Route.Home,
            badgeCount = 0
        ),
        BottomNavigationItem(
            label = R.string.music,
            icon = R.drawable.music,
            route = Route.Music,
            badgeCount = 0
        ),
        BottomNavigationItem(
            label = R.string.music_video,
            icon = R.drawable.music_video,
            route = Route.MusicVideo,
            badgeCount = 0
        ),
        BottomNavigationItem(
            label = R.string.profile,
            icon = R.drawable.profile,
            route = Route.Profile,
            badgeCount = 0
        ),
    )
}