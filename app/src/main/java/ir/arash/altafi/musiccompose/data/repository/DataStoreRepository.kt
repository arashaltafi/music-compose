package ir.arash.altafi.musiccompose.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ir.arash.altafi.musiccompose.data.model.UserInfoModel
import ir.arash.altafi.musiccompose.utils.EncryptionUtils
import ir.arash.altafi.musiccompose.utils.JsonUtils
import ir.arash.altafi.musiccompose.utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val encryptionUtils: EncryptionUtils,
    private val jsonUtils: JsonUtils
) : BaseRepository() {

    @Inject
    lateinit var jsonUtils1: JsonUtils

    // Token
    fun getTokenString(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                encryptionUtils.decrypt(preferences[PreferenceKeys.TOKEN] ?: "default_value")
            }.first()
        }
    }

    suspend fun setToken(value: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.TOKEN] = encryptionUtils.encrypt(value)
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            encryptionUtils.decrypt(preferences[PreferenceKeys.TOKEN] ?: "default_value")
        }
    }

    // info
    suspend fun setUserInfo(value: UserInfoModel) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USERINFO] = encryptionUtils.encrypt(jsonUtils.toJson(value))
        }
    }

    fun getUserInfo(): Flow<UserInfoModel> {
        return dataStore.data.map { preferences ->
            val json =
                encryptionUtils.decrypt(preferences[PreferenceKeys.USERINFO] ?: "default_value")
            jsonUtils.getSafeObject<UserInfoModel>(json).getOrElse {
                UserInfoModel()
            }
        }
    }

    fun getUserInfoResponse(): UserInfoModel {
        return runBlocking {
            val json = dataStore.data.map { preferences ->
                encryptionUtils.decrypt(preferences[PreferenceKeys.USERINFO] ?: "default_value")
            }.first()
            jsonUtils.getSafeObject<UserInfoModel>(json).getOrElse {
                UserInfoModel()
            }
        }
    }
}

object PreferenceKeys {
    val TOKEN = stringPreferencesKey("user_token")
    val USERINFO = stringPreferencesKey("user_info")
}