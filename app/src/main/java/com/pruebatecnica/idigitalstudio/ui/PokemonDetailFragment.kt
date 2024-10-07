package com.pruebatecnica.idigitalstudio.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pruebatecnica.idigitalstudio.MainActivity
import com.pruebatecnica.idigitalstudio.R
import com.pruebatecnica.idigitalstudio.data.local.PokemonDatabase
import com.pruebatecnica.idigitalstudio.data.model.PokemonDetail
import com.pruebatecnica.idigitalstudio.data.remote.PokemonApi
import com.pruebatecnica.idigitalstudio.data.remote.PokemonDetailsApi
import com.pruebatecnica.idigitalstudio.data.remote.RetrofitClient
import com.pruebatecnica.idigitalstudio.data.repository.PokemonRepository
import com.pruebatecnica.idigitalstudio.ui.adapter.StatsAdapter
import com.pruebatecnica.idigitalstudio.ui.viewmodel.PokemonDetailViewModel
import com.pruebatecnica.idigitalstudio.ui.viewmodel.PokemonDetailViewModelFactory

class PokemonDetailFragment : Fragment() {

    companion object {
        private const val ARG_POKEMON_ID = "pokemonId"

        fun newInstance(pokemonId: Int): PokemonDetailFragment {
            val fragment = PokemonDetailFragment()
            val args = Bundle()
            args.putInt(ARG_POKEMON_ID, pokemonId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var contentView: ScrollView
    private lateinit var fabShare: FloatingActionButton
    private lateinit var statsAdapter: StatsAdapter
    private lateinit var favoriteButton: ToggleButton
    private lateinit var sharedPreferences: SharedPreferences
    private var currentPokemon: PokemonDetail? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        contentView = view.findViewById(R.id.contentScrollView)
        fabShare = view.findViewById(R.id.fabShare)
        favoriteButton = view.findViewById(R.id.favoriteButton)

        sharedPreferences = requireContext().getSharedPreferences("PokemonPrefs", Context.MODE_PRIVATE)


        contentView.visibility = View.INVISIBLE
        fabShare.visibility = View.INVISIBLE
        favoriteButton.visibility = View.INVISIBLE
        loadingProgressBar.visibility = View.VISIBLE



        val topBackButton: ImageButton = view.findViewById(R.id.topBackButton)
        topBackButton.setOnClickListener {
            handleBackNavigation()
        }

        val backButton: Button = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            handleBackNavigation()
        }



        setupViewModel()
        setupStatsRecyclerView()
        setupShareButton()
        setupFavoriteButton()

        val pokemonId = arguments?.getInt(ARG_POKEMON_ID) ?: return
        viewModel.loadPokemonDetails(pokemonId)

        observeViewModel()
    }

    fun handleBackNavigation() {
        if (!isAdded) return

        val mainActivity = activity as? MainActivity
        if (mainActivity != null) {
            mainActivity.onBackPressed()
        } else {
            // Si por alguna razón no podemos obtener la MainActivity, hacemos un pop del fragmento actual
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupViewModel() {
        val pokemonApi = RetrofitClient.createService(PokemonApi::class.java)
        val pokemonDetailsApi = RetrofitClient.createService(PokemonDetailsApi::class.java)
        val database = PokemonDatabase.getDatabase(requireContext())
        val pokemonDao = database.pokemonDao()
        val repository = PokemonRepository(pokemonApi, pokemonDetailsApi, pokemonDao)
        val viewModelFactory = PokemonDetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PokemonDetailViewModel::class.java)
    }

    private fun setupStatsRecyclerView() {
        statsAdapter = StatsAdapter()
        view?.findViewById<RecyclerView>(R.id.statsRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = statsAdapter
        }
    }

    private fun setupShareButton() {
        fabShare.setOnClickListener {
            currentPokemon?.let { pokemon ->
                sharePokemon(pokemon)
            } ?: run {
                Toast.makeText(context, "La información del Pokémon aún no está disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFavoriteButton() {
        favoriteButton.setOnCheckedChangeListener { _, isChecked ->
            currentPokemon?.let { pokemon ->
                if (isChecked) {
                    addToFavorites(pokemon)
                } else {
                    removeFromFavorites(pokemon)
                }
            }
        }
    }

    private fun addToFavorites(pokemon: PokemonDetail) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("favorite_${pokemon.id}", true)
        editor.apply()
        Toast.makeText(context, "${pokemon.name} añadido a favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFavorites(pokemon: PokemonDetail) {
        val editor = sharedPreferences.edit()
        editor.remove("favorite_${pokemon.id}")
        editor.apply()
        Toast.makeText(context, "${pokemon.name} eliminado de favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun sharePokemon(pokemon: PokemonDetail) {
        val shareText = buildString {
            append("¡Mira este Pokémon!\n\n")
            append("Nombre: ${pokemon.name.capitalize()}\n")
            append("ID: ${pokemon.id}\n")
            append("Altura: ${pokemon.height / 10.0} m\n")
            append("Peso: ${pokemon.weight / 10.0} kg\n")
            append("Experiencia base: ${pokemon.baseExperience}\n")
            append("Generación: ${pokemon.generation}\n\n")

            append("Habilidades: ${pokemon.abilities.joinToString(", ")}\n\n")

            append("Estadísticas:\n")
            pokemon.stats.forEach { stat ->
                append("${stat.name}: ${stat.baseStat}\n")
            }

            append("\nMás información en: https://www.pokemon.com/es/pokedex/${pokemon.id}")
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Información sobre ${pokemon.name.capitalize()}")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(shareIntent, "Compartir Pokémon"))
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemon ->
            updateUiWithPokemonDetails(pokemon)
            // Muestra el contenido, el FAB y el botón de favorito con una animación de fade
            contentView.alpha = 0f
            contentView.visibility = View.VISIBLE
            contentView.animate().alpha(1f).setDuration(300).start()

            fabShare.alpha = 0f
            fabShare.visibility = View.VISIBLE
            fabShare.animate().alpha(1f).setDuration(300).start()

            favoriteButton.alpha = 0f
            favoriteButton.visibility = View.VISIBLE
            favoriteButton.animate().alpha(1f).setDuration(300).start()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingProgressBar.visibility = View.VISIBLE
                fabShare.visibility = View.INVISIBLE
                favoriteButton.visibility = View.INVISIBLE
            } else {
                // Oculta el indicador de carga con una animación de fade
                loadingProgressBar.animate().alpha(0f).setDuration(300).withEndAction {
                    loadingProgressBar.visibility = View.GONE
                }.start()
            }
            toggleUiInteraction(!isLoading)
        }
    }

    private fun updateUiWithPokemonDetails(pokemon: PokemonDetail) {
        currentPokemon = pokemon
        view?.apply {
            findViewById<ImageView>(R.id.pokemonImage).loadImage(pokemon.imageUrl)
            findViewById<TextView>(R.id.pokemonName).text = pokemon.name
            findViewById<TextView>(R.id.pokemonId).text = pokemon.id.toString()
            findViewById<TextView>(R.id.pokemonHeight).text = "Altura: ${pokemon.height / 10.0} m"
            findViewById<TextView>(R.id.pokemonWeight).text = "Peso: ${pokemon.weight / 10.0} kg"
            findViewById<TextView>(R.id.pokemonExperience).text = "Experiencia base: ${pokemon.baseExperience}"
            findViewById<TextView>(R.id.pokemonGeneration).text = pokemon.generation

            setupChips(findViewById(R.id.abilitiesChipGroup), pokemon.abilities)
            setupChips(findViewById(R.id.movesChipGroup), pokemon.moves)
            setupChips(findViewById(R.id.itemsChipGroup), pokemon.heldItems)
            statsAdapter.setStats(pokemon.stats)

            favoriteButton.isChecked = sharedPreferences.getBoolean("favorite_${pokemon.id}", false)
        }
    }

    private fun toggleUiInteraction(enabled: Boolean) {
        view?.apply {
            isEnabled = enabled
            alpha = if (enabled) 1.0f else 0.5f
        }
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.imgdefault)
            .error(R.drawable.imgdefault)
            .into(this)
    }

    private fun setupChips(chipGroup: ChipGroup, items: List<String>) {
        chipGroup.removeAllViews()
        items.forEach { item ->
            val chip = Chip(context).apply {
                text = item
                setChipBackgroundColorResource(R.color.pokemon_orange)
                setChipStrokeColorResource(R.color.pokemon_pink)
                chipStrokeWidth = resources.getDimension(R.dimen.chip_stroke_width)
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
            }
            chipGroup.addView(chip)
        }
    }
}