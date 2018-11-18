package com.bignerdranch.android.yandexkotlinproject

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApiService {

    @GET("translate")
    fun translateText (@Query("key") key: String,
                       @Query("text") text: String,
                       @Query("lang") lang: String,
                       @Query("format") format: String): Observable<TranslateModel.Result>

    //companion object to create the YandexApiService
    companion object {
        fun create(): YandexApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                    .build()

            return retrofit.create(YandexApiService::class.java)
        }
    }
}