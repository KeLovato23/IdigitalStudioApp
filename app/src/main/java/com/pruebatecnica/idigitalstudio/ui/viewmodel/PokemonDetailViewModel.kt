package com.pruebatecnica.idigitalstudio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pruebatecnica.idigitalstudio.data.model.PokemonDetail
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonDetails = MutableLiveData<PokemonDetail>()
    val pokemonDetails: LiveData<PokemonDetail> = _pokemonDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val details = repository.getPokemonDetails(pokemonId)
                _pokemonDetails.value = details
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }
}