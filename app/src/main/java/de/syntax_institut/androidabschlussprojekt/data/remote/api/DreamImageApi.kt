package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


// Base URL
const val BASE_URL = "https://api.openai.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// für Endpunkte
interface APIService {
    @Headers("Content-Type: application/json")
    @POST("images/generations")
    suspend fun generateImage(
        @Header("Authorization") authHeader: String,
        @Body request: DreamImageRequest
    ): Response<DreamImageRequest>
}

object DreamsApi {
    val retrofitService: APIService by lazy { retrofit.create(APIService::class.java) }
}
