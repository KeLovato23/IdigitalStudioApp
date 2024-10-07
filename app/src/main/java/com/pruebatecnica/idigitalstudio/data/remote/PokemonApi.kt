package com.pruebatecnica.idigitalstudio.data.remote

import com.pruebatecnica.idigitalstudio.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonResponse
}