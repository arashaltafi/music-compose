package ir.arash.altafi.musiccompose.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import ir.arash.altafi.musiccompose.utils.EncryptionUtils

val Context.dataStore by preferencesDataStore(name = "secure_preferences")

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore

    @Provides
    @Singleton
    fun provideEncryptionUtils(): EncryptionUtils {
        return EncryptionUtils()
    }
}