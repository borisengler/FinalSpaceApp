package com.example.finalfinalspace.datamanagment.images

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ImageRetrofitClient {
    var BASE_URL:String="http://finalspaceapi.com/api/episode/image/"
    val getClient: ImageAPI
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ImageAPI::class.java)
        }
}