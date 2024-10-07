package com.pruebatecnica.idigitalstudio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pruebatecnica.idigitalstudio.MainActivity
import com.pruebatecnica.idigitalstudio.R
import com.pruebatecnica.idigitalstudio.ui.adapter.PokemonAdapter
import com.pruebatecnica.idigitalstudio.ui.viewmodel.PokemonViewModel

class MainFragment : Fragment() {

    private lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var pokemonAdapter: PokemonAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView(view)
        observeViewModel()
    }

    private fun setupViewModel() {
        pokemonViewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        pokemonAdapter = PokemonAdapter { pokemon ->
            (activity as? MainActivity)?.showPokemonDetails(pokemon)
        }
        recyclerView.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (pokemonAdapter.getItemViewType(position)) {
                            PokemonAdapter.VIEW_TYPE_POKEMON -> 1
                            PokemonAdapter.VIEW_TYPE_LOADING -> 2
                            else -> -1
                        }
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && dy > 0
                        && pokemonAdapter.itemCount >= 20  // Asegurarse de que ya se han cargado algunos items
                    ) {
                        pokemonViewModel.loadMorePokemons()
                    }
                }
            })
        }
    }

    private fun observeViewModel() {
        pokemonViewModel.pokemons.observe(viewLifecycleOwner, Observer { pokemonItems ->
            pokemonAdapter.submitList(pokemonItems)
        })
    }
}