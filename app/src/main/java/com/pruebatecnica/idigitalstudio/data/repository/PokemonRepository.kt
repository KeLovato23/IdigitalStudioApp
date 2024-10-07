package com.pruebatecnica.idigitalstudio.data.repository

import com.pruebatecnica.idigitalstudio.data.local.PokemonDao
import com.pruebatecnica.idigitalstudio.data.model.PokemonEntity
import com.pruebatecnica.idigitalstudio.data.model.Pokemon
import com.pruebatecnica.idigitalstudio.data.model.PokemonDetail
import com.pruebatecnica.idigitalstudio.data.model.Stat
import com.pruebatecnica.idigitalstudio.data.remote.PokemonApi
import com.pruebatecnica.idigitalstudio.data.remote.PokemonDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val pokemonApi: PokemonApi,
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDao: PokemonDao
) {
    suspend fun getPokemons(limit: Int, offset: Int): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val cachedPokemons = pokemonDao.getPokemons(offset, limit)
            if (cachedPokemons.isNotEmpty()) {
                cachedPokemons.map { it.toPokemon() }
            } else {
                val response = pokemonApi.getPokemons(limit, offset)
                val pokemons = response.results.map { pokemon ->
                    val pokemonId = getPokemonIdFromUrl(pokemon.url)
                    val pokemonDetails = pokemonDetailsApi.getPokemonDetails(pokemonId)
                    val abilities = pokemonDetails.abilities.map { it.ability.name }
                    PokemonEntity(
                        id = pokemonId,
                        url = pokemon.url,
                        name = pokemon.name,
                        abilities = abilities.joinToString(", "),
                        imageUrl = pokemonDetails.sprites.front_default ?: ""
                    )
                }
                pokemonDao.insertPokemons(pokemons)
                pokemons.map { it.toPokemon() }
            }
        }
    }

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetail {
        return withContext(Dispatchers.IO) {
            val response = pokemonDetailsApi.getPokemonDetails(pokemonId)
            val speciesResponse = pokemonDetailsApi.getPokemonSpecies(pokemonId)
            PokemonDetail(
                id = response.id,
                name = response.name,
                height = response.height,
                weight = response.weight,
                baseExperience = response.base_experience,
                imageUrl = response.sprites.front_default ?: "",
                abilities = response.abilities.map { it.ability.name },
                moves = response.moves.map { it.move.name },
                heldItems = response.held_items.map { it.item.name },
                generation = speciesResponse.generation.name.capitalize(),

                stats = response.stats.map {
                    Stat(
                        name = it.stat.name.replace('-', ' ').capitalize(),
                        baseStat = it.base_stat
                    )
                },

            )
        }
    }

    private fun PokemonEntity.toPokemon(): Pokemon {
        val abilities = this.abilities.split(", ")
        return Pokemon(
            id = this.id,
            name = this.name,
            url = this.url,
            abilities = abilities,
            imageUrl = this.imageUrl
        )
    }

    private fun getPokemonIdFromUrl(url: String): Int {
        val parts = url.split("/")
        return parts[parts.size - 2].toInt()
    }
}