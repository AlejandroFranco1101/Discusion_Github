package com.example.discusion_githubpublic

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que mapea un repositorio público obtenido desde la API de GitHub:
 * Endpoint: https://api.github.com/users/{username}/repos
 */
data class RepoResponse(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("full_name")
    val fullName: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("stargazers_count")
    val starsCount: Int? = 0,

    @SerializedName("forks_count")
    val forksCount: Int? = 0,

    @SerializedName("private")
    val isPrivate: Boolean? = false,

    @SerializedName("owner")
    val owner: Owner? = null
) {
    /**
     * 🌟 PLUS EXTRA: Helper para mostrar descripción segura o texto alternativo.
     */
    fun getSafeDescription(): String {
        return if (!description.isNullOrBlank()) description else "Sin descripción disponible"
    }

    /**
     * 🌟 PLUS EXTRA: Helper para mostrar el lenguaje de programación principal.
     */
    fun getSafeLanguage(): String {
        return if (!language.isNullOrBlank()) language else "Código variado"
    }
}
