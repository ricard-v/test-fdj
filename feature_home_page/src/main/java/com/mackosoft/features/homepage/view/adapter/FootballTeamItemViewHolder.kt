package com.mackosoft.features.homepage.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mackosoft.core.image.GlideApp
import com.mackosoft.features.homepage.databinding.ItemViewFootballTeamBinding
import com.mackosoft.features.homepage.model.entities.FootballTeamEntity

class FootballTeamItemViewHolder(
    private val binding: ItemViewFootballTeamBinding,
    onItemClickedListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                onItemClickedListener.invoke(bindingAdapterPosition)
            }
        }
    }

    fun bindTeam(footballTeamEntity: FootballTeamEntity) {
        footballTeamEntity.teamBadgeUrl?.let { url ->
            GlideApp.with(binding.root).load(url).into(binding.root)
        } ?: run {
            clearImage()
        }
        binding.root.contentDescription = footballTeamEntity.teamName
    }

    fun recycle() {
        clearImage()
        binding.root.contentDescription = null
    }

    private fun clearImage() {
        GlideApp.with(binding.root).clear(binding.root) // cancel any ongoing request
        binding.root.setImageDrawable(null)
    }
}