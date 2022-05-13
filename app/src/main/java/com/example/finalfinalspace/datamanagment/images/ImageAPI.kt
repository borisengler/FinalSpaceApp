package com.example.finalfinalspace.datamanagment.images

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageAPI {
    @GET
    fun downloadImage(@Url fileUrl: String): Call<ResponseBody>
}