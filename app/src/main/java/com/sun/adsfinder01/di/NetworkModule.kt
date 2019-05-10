package com.sun.adsfinder01.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sun.adsfinder01.data.repository.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

val networkModule = module {
    single { createApiService() }
}

const val BASE_URL = "https://spring-boot-wall-tags.herokuapp.com/adsharingspace/"

const val CONTENT_TYPE = "Content-Type"

const val VALUE = "application/json"

const val TIME_REQUEST = 60L

private fun createApiService(): ApiService {

    val client = OkHttpClient.Builder()
        .addInterceptor(StethoInterceptor())
        .readTimeout(TIME_REQUEST, SECONDS)
        .writeTimeout(TIME_REQUEST, SECONDS)
        .connectTimeout(TIME_REQUEST, SECONDS)

    client.addInterceptor { chain ->
        val original: Request = chain.request()
        val request = original.newBuilder()
            .header(CONTENT_TYPE, VALUE)
            .method(original.method(), original.body())
            .build()
        return@addInterceptor chain.proceed(request)
    }

    val retrofit = Retrofit.Builder()
        .client(client.build())
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
