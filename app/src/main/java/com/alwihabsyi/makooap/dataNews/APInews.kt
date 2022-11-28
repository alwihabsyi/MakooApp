package com.alwihabsyi.makooap.dataNews

import android.telecom.Call
import retrofit2.http.GET

interface APInews {
    @GET("/v2/top-headlines?country=id&category=business&apiKey=32319836f7ee408ca2a19ab3f74b47df")
    fun getNews(): retrofit2.Call<Response>
}