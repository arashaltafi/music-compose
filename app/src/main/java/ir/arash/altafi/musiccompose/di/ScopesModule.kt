package ir.arash.altafi.musiccompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.arash.altafi.musiccompose.di.DefaultDispatcher
import ir.arash.altafi.musiccompose.di.DefaultScope
import ir.arash.altafi.musiccompose.di.IoDispatcher
import ir.arash.altafi.musiccompose.di.IoScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScopesModule {

    @DefaultScope
    @Singleton
    @Provides
    fun providesDefaultCoroutineScope(
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(dispatcher + SupervisorJob())
    }

    @IoScope
    @Singleton
    @Provides
    fun providesIoCoroutineScope(
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(dispatcher + SupervisorJob())
    }
}