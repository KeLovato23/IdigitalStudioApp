package com.pruebatecnica.idigitalstudio.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pruebatecnica.idigitalstudio.R
import com.pruebatecnica.idigitalstudio.data.local.PokemonDatabase
import com.pruebatecnica.idigitalstudio.data.model.PokemonAdapterItem
import com.pruebatecnica.idigitalstudio.data.remote.PokemonApi
import com.pruebatecnica.idigitalstudio.data.remote.PokemonDetailsApi
import com.pruebatecnica.idigitalstudio.data.remote.RetrofitClient
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import com.pruebatecnica.idigitalstudio.ui.adapter.PokemonAdapter
import com.pruebatecnica.idigitalstudio.ui.viewmodel.FavoritePokemonViewModel
import com.pruebatecnica.idigitalstudio.ui.viewmodel.FavoritePokemonViewModelFactory

class FavoritePokemonFragment : Fragment() {

    private lateinit var viewModel: FavoritePokemonViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyText: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_pokemon, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
            ?: throw IllegalStateException("favoriteRecyclerView not found in layout")
        progressBar = view.findViewById(R.id.progressBar)
            ?: throw IllegalStateException("progressBar not found in layout")
        emptyText = view.findViewById(R.id.emptyText)
            ?: throw IllegalStateException("emptyText not found in layout")
        sharedPreferences = requireContext().getSharedPreferences("PokemonPrefs", Context.MODE_PRIVATE)

        view.findViewById<ImageButton>(R.id.topBackButton)?.setOnClickListener {
            activity?.onBackPressed()
        } ?: throw IllegalStateException("topBackButton not found in layout")

        view.findViewById<Button>(R.id.backButton)?.setOnClickListener {
            activity?.onBackPressed()
        } ?: throw IllegalStateException("topBackButton not found in layout")


        setupRecyclerView()
        setupViewModel()
        loadFavorites()
    }
    private fun setupRecyclerView() {
        adapter = PokemonAdapter { pokemon ->
            val detailFragment = PokemonDetailFragment.newInstance(pokemon.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }
    private fun setupViewModel() {
        val pokemonApi = RetrofitClient.createService(PokemonApi::class.java)
        val pokemonDetailsApi = RetrofitClient.createService(PokemonDetailsApi::class.java)
        val database = PokemonDatabase.getDatabase(requireContext())
        val pokemonDao = database.pokemonDao()
        val repository = PokemonRepository(pokemonApi, pokemonDetailsApi, pokemonDao)
        val viewModelFactory = FavoritePokemonViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritePokemonViewModel::class.java)

        viewModel.favoritePokemons.observe(viewLifecycleOwner) { pokemonItems ->
            if (pokemonItems.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showPokemons(pokemonItems)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun loadFavorites() {
        val favoriteIds = sharedPreferences.all
            .filter { it.key.startsWith("favorite_") && it.value == true }
            .map { it.key.removePrefix("favorite_").toInt() }

        viewModel.loadFavoritePokemons(favoriteIds)
    }

    private fun showEmptyState() {
        recyclerView.visibility = View.GONE
        emptyText.visibility = View.VISIBLE
    }

    private fun showPokemons(pokemonItems: List<PokemonAdapterItem>) {
        recyclerView.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
        adapter.submitList(pokemonItems)
    }

    companion object {
        fun newInstance() = FavoritePokemonFragment()
    }
}