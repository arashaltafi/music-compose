package ir.arash.altafi.musiccompose.di

import ir.arash.altafi.musiccompose.data.api.CelebrityService
import ir.arash.altafi.musiccompose.data.api.PagingService
import ir.arash.altafi.musiccompose.data.api.TestService
import ir.arash.altafi.musiccompose.data.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideTestService(retrofit: Retrofit): TestService =
        retrofit.create(TestService::class.java)

    @Singleton
    @Provides
    fun provideCelebrityService(retrofit: Retrofit): CelebrityService =
        retrofit.create(CelebrityService::class.java)

    @Singleton
    @Provides
    fun providePagingService(retrofit: Retrofit): PagingService =
        retrofit.create(PagingService::class.java)

}
