package com.pruebatecnica.idigitalstudio.data.model

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>,

)