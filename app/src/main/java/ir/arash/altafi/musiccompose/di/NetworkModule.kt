package ir.arash.altafi.musiccompose.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.inject.Named
import ir.arash.altafi.musiccompose.BuildConfig
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.utils.DeviceInfo
import okhttp3.ConnectionSpec
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("BaseURL")
    fun provideBaseURL(): String {
        return BuildConfig.BASE_URL
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        context: Context,
        dataStoreRepository: DataStoreRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .addInterceptor { chain ->
                val token = dataStoreRepository.getTokenString()
                val original = chain.request()
                val builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("PackageName", context.packageName)
                    .addHeader(
                        "User-Agent",
                        "${DeviceInfo.getDeviceManufacturer()} " +
                                "${DeviceInfo.getDeviceModel()} - " +
                                "Android ${DeviceInfo.getDeviceAndroidVersion()} - " +
                                "${DeviceInfo.getAndroidID(context)}"
                    )

                // only add Authorization if we have a non‐blank token
                if (token.isNotBlank() && token != "default_value") {
                    builder.addHeader("Authorization", "Bearer $token")
                }

                val request = builder.build()
                chain.proceed(request)
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseURL") baseURL: String,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}