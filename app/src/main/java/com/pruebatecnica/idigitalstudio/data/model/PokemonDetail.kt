package com.pruebatecnica.idigitalstudio.data.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val imageUrl: String,
    val abilities: List<String>,
    val moves: List<String>,
    val heldItems: List<String>,
    val generation: String,
    val stats: List<Stat>
)
data class Stat(
    val name: String,
    val baseStat: Int
)