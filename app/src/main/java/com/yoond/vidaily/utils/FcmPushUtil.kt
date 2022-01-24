package com.yoond.vidaily.utils

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.PushToken
import com.google.gson.Gson
import com.yoond.vidaily.data.PushItem
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class FcmPushUtil {
    private val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    private val url = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = ""
    private val gson = Gson()
    private val okHttpClient = OkHttpClient.Builder().build()

    companion object {
        val instance = FcmPushUtil()
    }

    fun sendPush(destinationUid: String, title: String, message: String) {
        Amplify.API.query(ModelQuery.get(PushToken::class.java, destinationUid),
            { response ->
                if (response.hasData() && response.data != null) {
                    val token = response.data.token
                    val push = PushItem()
                    push.to = token
                    push.notification.title = title
                    push.notification.body = message

                    val body = gson.toJson(push).toRequestBody(JSON)
                    val request = Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "key=$serverKey")
                        .url(url)
                        .post(body)
                        .build()

                    okHttpClient.newCall(request).enqueue(object: Callback {
                        override fun onResponse(call: Call, response: Response) {
                            Log.i("FCM_PUSH_UTIL", "okHttpClient call response: $response")
                        }

                        override fun onFailure(call: Call, e: IOException) {
                            Log.e("FCM_PUSH_UTIL", "okHttpClient call failed", e)
                        }
                    })
                }
            },
            { Log.e("FCM_PUSH_UTIL", "sendPush failed", it) }
        )
    }
}