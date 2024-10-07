package com.pruebatecnica.idigitalstudio.data.model


sealed class PokemonAdapterItem {
    data class PokemonItem(val pokemon: Pokemon) : PokemonAdapterItem()
    object LoadingItem : PokemonAdapterItem()
}