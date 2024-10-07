package com.pruebatecnica.idigitalstudio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pruebatecnica.idigitalstudio.data.model.Pokemon
import com.pruebatecnica.idigitalstudio.data.model.PokemonAdapterItem
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class FavoritePokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _favoritePokemons = MutableLiveData<List<PokemonAdapterItem>>()
    val favoritePokemons: LiveData<List<PokemonAdapterItem>> = _favoritePokemons

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadFavoritePokemons(ids: List<Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val pokemons = ids.map { id -> repository.getPokemonDetails(id) }
                _favoritePokemons.value = pokemons.map { pokemonDetail ->
                    PokemonAdapterItem.PokemonItem(
                        Pokemon(
                            id = pokemonDetail.id,
                            name = pokemonDetail.name,
                            url = "https://pokeapi.co/api/v2/pokemon/${pokemonDetail.id}/",
                            abilities = pokemonDetail.abilities,
                            imageUrl = pokemonDetail.imageUrl
                        )
                    )
                }
            } catch (e: Exception) {
                // Manejar el error aquí
            } finally {
                _isLoading.value = false
            }
        }
    }
}