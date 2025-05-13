package ir.arash.altafi.musiccompose.data.repository

import ir.arash.altafi.musiccompose.data.api.AuthService
import ir.arash.altafi.musiccompose.utils.base.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService,
) : BaseRepository() {


}