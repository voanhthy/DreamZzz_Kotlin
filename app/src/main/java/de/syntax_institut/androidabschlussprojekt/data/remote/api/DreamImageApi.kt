package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


private val apiKey = "Bearer ${BuildConfig.API_KEY}"

// Base URL
const val BASE_URL = "https://api.openai.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// OkHttpClient auf 60s setzen, da sonst TimeOut (nach 10s)
private val client = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

// für Endpunkte
interface APIService {
    @Headers("Content-Type: application/json")
    @POST("images/generations")         // Endpunkt für Bildgenerierung
    suspend fun generateImage(
        @Header("Authorization") authHeader: String = apiKey,
        @Body request: DreamImageRequest
    ): Response<DreamImageResponse>
}

object DreamImageApi {
    val retrofitService: APIService by lazy { retrofit.create(APIService::class.java) }
}


/*
curl https://api.openai.com/v1/images/generations \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "gpt-image-1",
    "prompt": "A cute baby sea otter",
    "n": 1,
    "size": "1024x1024"
  }'
 */