package com.sun.adsfinder01.di

import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sun.adsfinder01.data.repository.api.ApiService
import com.sun.adsfinder01.util.Constants
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

private fun createApiService(): ApiService {
    val client = OkHttpClient.Builder()
        .addInterceptor(StethoInterceptor())
        .readTimeout(Constants.TIME_REQUEST, SECONDS)
        .writeTimeout(Constants.TIME_REQUEST, SECONDS)
        .connectTimeout(Constants.TIME_REQUEST, SECONDS)

    client.addInterceptor { chain ->
        val original: Request = chain.request()
        val request = original.newBuilder()
            .header(ApiService.CONTENT_TYPE, ApiService.VALUE)
            .method(original.method(), original.body())
            .build()
        return@addInterceptor chain.proceed(request)
    }

    val retrofit = Retrofit.Builder()
        .client(client.build())
        .baseUrl(ApiService.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
