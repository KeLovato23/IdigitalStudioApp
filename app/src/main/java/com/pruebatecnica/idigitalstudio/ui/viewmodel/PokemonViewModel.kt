
package com.pruebatecnica.idigitalstudio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pruebatecnica.idigitalstudio.data.model.PokemonAdapterItem
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    private val _pokemons = MutableLiveData<List<PokemonAdapterItem>>()
    val pokemons: LiveData<List<PokemonAdapterItem>> = _pokemons

    private var offset = 0
    private val limit = 20
    private var isLoading = false

    init {
        loadMorePokemons()
    }

    fun loadMorePokemons() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val newPokemonList = pokemonRepository.getPokemons(limit, offset)
                val currentList = _pokemons.value?.filterIsInstance<PokemonAdapterItem.PokemonItem>()?.map { it.pokemon } ?: emptyList()
                val updatedList = (currentList + newPokemonList).distinctBy { it.url }

                val adapterItems = updatedList.map { PokemonAdapterItem.PokemonItem(it) as PokemonAdapterItem }.toMutableList()

                if (newPokemonList.size == limit) {
                    adapterItems.add(PokemonAdapterItem.LoadingItem)
                }

                _pokemons.value = adapterItems
                offset += limit
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}