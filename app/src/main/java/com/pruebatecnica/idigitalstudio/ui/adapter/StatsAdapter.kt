package com.pruebatecnica.idigitalstudio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pruebatecnica.idigitalstudio.R
import com.pruebatecnica.idigitalstudio.data.model.Stat

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatViewHolder>() {
    private var stats: List<Stat> = emptyList()

    fun setStats(stats: List<Stat>) {
        this.stats = stats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return StatViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(stats[position])
    }

    override fun getItemCount() = stats.size

    class StatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.statName)
        private val valueTextView: TextView = itemView.findViewById(R.id.statValue)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.statProgressBar)

        fun bind(stat: Stat) {
            nameTextView.text = stat.name
            valueTextView.text = stat.baseStat.toString()
            progressBar.progress = stat.baseStat
        }
    }
}