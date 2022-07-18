package com.example.finalfinalspace.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.EpisodeCardviewBinding
import com.example.finalfinalspace.databinding.FragmentEpisodeBinding
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
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
    ): EpisodesRWAdapter.EpisodeViewHolder {
        return EpisodeViewHolder(EpisodeCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false), onListener)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EpisodeViewHolder(private val binding: EpisodeCardviewBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(info: EpisodesInfo) {
            binding.episodeName.text = info.name
            binding.episodeDate.text = info.airDate
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        onListener = listener
    }

    class EpisodesDiffCallback : DiffUtil.ItemCallback<EpisodesInfo>() {
        override fun areItemsTheSame(oldItem: EpisodesInfo, newItem: EpisodesInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodesInfo, newItem: EpisodesInfo): Boolean {
            return oldItem == newItem
        }

    }

}