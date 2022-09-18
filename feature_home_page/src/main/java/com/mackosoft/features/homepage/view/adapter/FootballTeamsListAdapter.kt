package com.mackosoft.features.homepage.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mackosoft.features.homepage.databinding.ItemViewFootballTeamBinding
import com.mackosoft.features.homepage.model.entities.FootballTeamEntity

class FootballTeamsListAdapter(
    private val onItemClickedListener: (FootballTeamEntity) -> Unit,
) : ListAdapter<FootballTeamEntity, FootballTeamItemViewHolder>(
    DiffCallback(),
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballTeamItemViewHolder {
        return FootballTeamItemViewHolder(
            binding = ItemViewFootballTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onItemClickedListener = { position ->
                onItemClickedListener.invoke(getItem(position))
            }
        )
    }

    override fun onBindViewHolder(holder: FootballTeamItemViewHolder, position: Int) {
        holder.bindTeam(getItem(position))
    }

    override fun onViewRecycled(holder: FootballTeamItemViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }
}

private class DiffCallback : DiffUtil.ItemCallback<FootballTeamEntity>() {
    override fun areItemsTheSame(
        oldItem: FootballTeamEntity,
        newItem: FootballTeamEntity
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: FootballTeamEntity,
        newItem: FootballTeamEntity
    ) = oldItem == newItem
}