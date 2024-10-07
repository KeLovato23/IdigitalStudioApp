package com.pruebatecnica.idigitalstudio

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.pruebatecnica.idigitalstudio.data.local.PokemonDatabase
import com.pruebatecnica.idigitalstudio.data.model.Pokemon
import com.pruebatecnica.idigitalstudio.data.remote.PokemonApi
import com.pruebatecnica.idigitalstudio.data.remote.PokemonDetailsApi
import com.pruebatecnica.idigitalstudio.data.remote.RetrofitClient
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import com.pruebatecnica.idigitalstudio.ui.FavoritePokemonFragment
import com.pruebatecnica.idigitalstudio.ui.MainFragment
import com.pruebatecnica.idigitalstudio.ui.PokemonDetailFragment
import com.pruebatecnica.idigitalstudio.ui.viewmodel.PokemonViewModel
import com.pruebatecnica.idigitalstudio.ui.viewmodel.PokemonViewModelFactory



class MainActivity : AppCompatActivity() {
    private lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var favoritesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoritesButton = findViewById(R.id.favoritesButton)

        setupViewModel()
        setupFavoritesButton()

        if (savedInstanceState == null) {
            showMainContent()
        }
    }

    private fun setupViewModel() {
        val pokemonApi = RetrofitClient.createService(PokemonApi::class.java)
        val pokemonDetailsApi = RetrofitClient.createService(PokemonDetailsApi::class.java)
        val database = PokemonDatabase.getDatabase(this)
        val pokemonDao = database.pokemonDao()
        val pokemonRepository = PokemonRepository(pokemonApi, pokemonDetailsApi, pokemonDao)
        val pokemonViewModelFactory = PokemonViewModelFactory(pokemonRepository)

        pokemonViewModel = ViewModelProvider(this, pokemonViewModelFactory).get(PokemonViewModel::class.java)
    }

    private fun setupFavoritesButton() {
        favoritesButton.setOnClickListener {
            navigateToFavorites()
        }
    }

    private fun showMainContent() {
        favoritesButton.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()
    }

    private fun navigateToFavorites() {
        val favoritesFragment = FavoritePokemonFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, favoritesFragment)
            .addToBackStack(null)
            .commit()
        favoritesButton.visibility = View.GONE
    }

    fun showPokemonDetails(pokemon: Pokemon) {
        val detailFragment = PokemonDetailFragment.newInstance(pokemon.id)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.zoom_in,
                R.anim.zoom_out,
                R.anim.zoom_in,
                R.anim.zoom_out
            )
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
        favoritesButton.visibility = View.GONE
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (currentFragment) {
            is PokemonDetailFragment, is FavoritePokemonFragment -> {

                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                showMainContent()
            }
            is MainFragment -> {
                // Si estamos en el MainFragment, salir de la app
                super.onBackPressed()
            }
            else -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment is MainFragment) {
            favoritesButton.visibility = View.VISIBLE
        } else {
            favoritesButton.visibility = View.GONE
        }
    }
}