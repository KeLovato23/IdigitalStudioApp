package com.pruebatecnica.idigitalstudio.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val name: String,
    val abilities: String,
    val imageUrl: String
)