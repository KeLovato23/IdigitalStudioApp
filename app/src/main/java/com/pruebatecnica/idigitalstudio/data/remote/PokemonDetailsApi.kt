package com.pruebatecnica.idigitalstudio.data.remote

import com.pruebatecnica.idigitalstudio.data.model.NamedApiResource
import com.pruebatecnica.idigitalstudio.data.model.PokemonDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDetailsApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailsResponse
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}

data class PokemonSpeciesResponse(
    val generation: NamedApiResource
)