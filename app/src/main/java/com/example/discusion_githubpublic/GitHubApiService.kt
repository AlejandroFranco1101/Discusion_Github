package com.example.discusion_githubpublic

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que define las operaciones HTTP sobre la API REST pública de GitHub.
 */
interface GitHubApiService {

    /**
     * Obtiene el listado de repositorios públicos asociados a un nombre de usuario en GitHub.
     *
     * @param username Nombre de usuario u organización en GitHub (ej: "mojombo", "google", "square").
     * @param sort Orden de los repositorios devueltos (por defecto "updated" para ver los más recientes).
     * @return Call asíncrono que parsea la respuesta JSON en una lista List<RepoResponse>.
     */
    @GET("users/{username}/repos")
    fun getUserRepos(
        @Path("username") username: String,
        @Query("sort") sort: String = "updated"
    ): Call<List<RepoResponse>>
}
