package com.pruebatecnica.idigitalstudio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pruebatecnica.idigitalstudio.R
import com.pruebatecnica.idigitalstudio.data.model.Pokemon
import com.pruebatecnica.idigitalstudio.data.model.PokemonAdapterItem
class PokemonAdapter(
    private val onPokemonClicked: (Pokemon) -> Unit
) : ListAdapter<PokemonAdapterItem, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    companion object {
        const val VIEW_TYPE_POKEMON = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
                PokemonViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PokemonAdapterItem.PokemonItem -> (holder as PokemonViewHolder).bind(item)
            is PokemonAdapterItem.LoadingItem -> { /* No es necesario hacer nada aquí */ }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonAdapterItem.PokemonItem -> VIEW_TYPE_POKEMON
            is PokemonAdapterItem.LoadingItem -> VIEW_TYPE_LOADING
        }
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val abilitiesTextView: TextView = itemView.findViewById(R.id.abilitiesTextView)

        fun bind(pokemonItem: PokemonAdapterItem.PokemonItem) {
            val pokemon = pokemonItem.pokemon
            val capitalizedName = pokemon.name.replaceFirstChar { it.uppercase() }
            nameTextView.text = capitalizedName

            Glide.with(itemView)
                .load(pokemon.imageUrl)
                .placeholder(R.drawable.imgdefault)
                .error(R.drawable.imgdefault)
                .into(imageView)

            bindAbilities(pokemon.abilities)

            itemView.setOnClickListener {
                onPokemonClicked(pokemon)
            }
        }

        private fun bindAbilities(abilities: List<String>) {
            abilitiesTextView.text = abilities.joinToString(", ")
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonAdapterItem>() {
        override fun areItemsTheSame(oldItem: PokemonAdapterItem, newItem: PokemonAdapterItem): Boolean {
            return when {
                oldItem is PokemonAdapterItem.PokemonItem && newItem is PokemonAdapterItem.PokemonItem ->
                    oldItem.pokemon.url == newItem.pokemon.url
                oldItem is PokemonAdapterItem.LoadingItem && newItem is PokemonAdapterItem.LoadingItem -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: PokemonAdapterItem, newItem: PokemonAdapterItem): Boolean {
            return oldItem == newItem
        }
    }
}