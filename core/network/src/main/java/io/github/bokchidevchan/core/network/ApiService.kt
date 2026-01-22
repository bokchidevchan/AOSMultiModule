package io.github.bokchidevchan.core.network

import io.github.bokchidevchan.core.network.dto.ArticleDto
import io.github.bokchidevchan.core.network.dto.SearchResponseDto
import io.github.bokchidevchan.core.network.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDto

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<UserDto>

    @GET("articles")
    suspend fun getArticles(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<ArticleDto>

    @GET("articles/{id}")
    suspend fun getArticle(@Path("id") id: Long): ArticleDto

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): SearchResponseDto
}
