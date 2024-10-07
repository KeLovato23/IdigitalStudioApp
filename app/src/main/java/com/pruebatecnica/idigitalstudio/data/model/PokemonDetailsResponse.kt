package com.pruebatecnica.idigitalstudio.data.model

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val base_experience: Int,
    val sprites: Sprites,
    val abilities: List<AbilitySlot>,
    val moves: List<MoveSlot>,
    val held_items: List<HeldItem>,

    val species: NamedApiResource,
    val stats: List<StatResponse>
)

data class Sprites(
    val front_default: String?
)

data class AbilitySlot(
    val ability: NamedApiResource,
    val is_hidden: Boolean,
    val slot: Int
)

data class MoveSlot(
    val move: NamedApiResource
)

data class HeldItem(
    val item: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)

data class StatResponse(
    val base_stat: Int,
    val stat: NamedApiResource
)