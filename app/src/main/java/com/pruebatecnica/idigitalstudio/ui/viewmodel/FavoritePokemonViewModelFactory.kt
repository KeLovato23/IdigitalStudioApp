package com.pruebatecnica.idigitalstudio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository

class FavoritePokemonViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritePokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritePokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}