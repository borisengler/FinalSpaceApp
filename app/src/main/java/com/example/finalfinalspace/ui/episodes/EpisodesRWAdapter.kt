package com.example.finalfinalspace.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import com.example.finalfinalspace.databinding.ItemEpisodeBinding
import javax.inject.Inject

class EpisodesRWAdapter @Inject constructor() :
    ListAdapter<EpisodesInfo, EpisodesRWAdapter.EpisodeViewHolder>(EpisodesDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onListener: OnItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onListener
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EpisodeViewHolder(
        private val binding: ItemEpisodeBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var id: Int? = null

        init {
            binding.parent.setOnClickListener {
                id?.let { listener.onItemClick(it) }
            }
        }

        fun bind(info: EpisodesInfo) {
            id = info.episodeId
            binding.episodeName.text = info.name
            binding.episodeDate.text = info.airDate
            Glide.with(binding.root).load(info.imageUrl).into(binding.episodeImg)
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        onListener = listener
    }

    class EpisodesDiffCallback : DiffUtil.ItemCallback<EpisodesInfo>() {
        override fun areItemsTheSame(oldItem: EpisodesInfo, newItem: EpisodesInfo): Boolean {
            return oldItem.episodeId == newItem.episodeId
        }

        override fun areContentsTheSame(oldItem: EpisodesInfo, newItem: EpisodesInfo): Boolean {
            return oldItem == newItem
        }
    }
}
