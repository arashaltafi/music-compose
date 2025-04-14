package ir.arash.altafi.musiccompose.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ir.arash.altafi.musiccompose.BuildConfig
import ir.arash.altafi.musiccompose.data.api.CelebrityService
import ir.arash.altafi.musiccompose.data.api.PagingService
import ir.arash.altafi.musiccompose.data.api.UserService
import ir.arash.altafi.musiccompose.data.dataSource.TestDataSource
import ir.arash.altafi.musiccompose.data.db.TestDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.arash.altafi.musiccompose.data.repository.CelebrityRepository
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.data.repository.PagingRepository
import ir.arash.altafi.musiccompose.data.repository.TestRepository
import ir.arash.altafi.musiccompose.data.repository.UserRepository
import ir.arash.altafi.musiccompose.utils.EncryptionUtils
import ir.arash.altafi.musiccompose.utils.JsonUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        dataStore: DataStore<Preferences>,
        encryptionUtils: EncryptionUtils,
        jsonUtils: JsonUtils
    ) = DataStoreRepository(dataStore, encryptionUtils, jsonUtils)

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
    ) = UserRepository(userService)

    @Singleton
    @Provides
    fun providePagingRepository(
        userService: PagingService,
    ) = PagingRepository(userService)

    @Singleton
    @Provides
    fun provideTestRepository(
        testDataSource: TestDataSource,
        userDao: TestDetailDao,
    ) = TestRepository(testDataSource, userDao)

    @Singleton
    @Provides
    fun provideCelebrityRepository(
        celebrityService: CelebrityService,
    ) = CelebrityRepository(celebrityService)

    @Provides
    @Singleton
    fun provideServerUrl(): String {
        return BuildConfig.BASE_URL
    }
}