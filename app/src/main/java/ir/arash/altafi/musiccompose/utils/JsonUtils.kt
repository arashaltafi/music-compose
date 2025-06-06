package ir.arash.altafi.musiccompose.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import javax.inject.Inject
import kotlin.jvm.java
import kotlin.runCatching

class JsonUtils @Inject constructor(
    val gson: Gson
) {
    inline fun <reified T> getObject(json: String): T {
        return gson.fromJson(json, T::class.java)
    }

    inline fun <reified T> getObject(map: Map<*, *>): T {
        return gson.fromJson(toJson(map), T::class.java)
    }

    inline fun <reified T> getSafeObject(json: String): Result<T> {
        return runCatching { gson.fromJson(json, T::class.java) }
    }

    inline fun <reified T> getSafeObject(map: Map<*, *>): Result<T> {
        return runCatching { gson.fromJson(toJson(map), T::class.java) }
    }

    inline fun <reified MODEL> getObjectList(json: String): List<MODEL> {
        return gson.fromJson(json, object : TypeToken<ArrayList<MODEL>>() {}.type)
    }

    inline fun <reified MODEL> getSafeObjectList(json: String): Result<List<MODEL>> {
        return runCatching { gson.fromJson(json, object : TypeToken<ArrayList<MODEL>>() {}.type) }
    }

    fun toJson(obj: Any): String {
        return gson.toJson(obj)
    }

    fun toJsonTree(obj: Any): JsonElement {
        return gson.toJsonTree(obj)
    }

    fun toCustomJson(obj: Any): JSONObject {
        return JSONObject(gson.toJson(obj))
    }
}