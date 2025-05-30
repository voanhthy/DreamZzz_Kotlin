package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamAnalyzeRequest
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamAnalyzeResponse
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

private val apiKey = "Bearer ${BuildConfig.API_KEY}"

// Base URL
const val BASE_URL_ANALYZE = "https://api.openai.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_ANALYZE)
    .build()

interface APIServiceAnalyze {
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun analyzeImage(
        @Header("Authorization") authHeader: String = apiKey,
        @Body request: DreamAnalyzeRequest
    ): Response<DreamAnalyzeResponse>
}

object DreamAnalyzeApi {
    val retrofitServiceAnalyze: APIServiceAnalyze by lazy {
        retrofit.create(APIServiceAnalyze::class.java)
    }
}