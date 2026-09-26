package com.example.discusion_githubpublic

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton encargado de proveer la instancia configurada de Retrofit para GitHub API.
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"

    /**
     * 🌟 PLUS EXTRA: Configuración de OkHttp con timeouts explícitos para mayor estabilidad.
     */
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Instancia singleton de GitHubApiService generada mediante Retrofit.
     */
    val instance: GitHubApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GitHubApiService::class.java)
    }
}
