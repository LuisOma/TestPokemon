package com.example.newbase.data.dataSource.remote

import com.example.newbase.data.entities.PokemonDetail
import com.example.newbase.data.entities.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<PokemonResponse>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int = 1): Response<PokemonDetail>

}