package com.example.repolistchallenge.network.extensions

import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

fun RequestBody?.bodyToString(): String {
    var jsonString = "{}"

    if (this != null) {
        val buffer = Buffer()
        jsonString = try {
            writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "ResponserErrorHandler: bodyToString() did not work"
        }
    }

    if (jsonString.isEmpty())
        jsonString = "{}"

    return jsonString

}

fun JSONObject.replace(name: String, value: Any): JSONObject {
    if (this.has(name)) {
//        Log.e("removed JsonObject", this.getSerializable(name].toString())
        this.remove(name)
    }
    this.put(name, value)
//    Log.e("putted JsonObject", value.toString())
    return this
}

fun Request?.getJsonObject() = JSONObject(this?.body().bodyToString())