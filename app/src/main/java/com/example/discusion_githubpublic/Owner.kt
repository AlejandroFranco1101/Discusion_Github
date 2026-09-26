package com.example.discusion_githubpublic

import com.google.gson.annotations.SerializedName

/**
 * Representa el propietario (usuario u organización) de un repositorio en GitHub.
 */
data class Owner(
    @SerializedName("login")
    val login: String? = null,

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null
)
