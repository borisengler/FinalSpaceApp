package com.example.finalfinalspace.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
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
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.episode_cardview, parent, false)
        return EpisodeViewHolder(view, onListener)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class EpisodeViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var episodeNameView: TextView = itemView.findViewById(R.id.episode_name)
        var episodeDateView: TextView = itemView.findViewById(R.id.episode_date)
//        var episodeImage: ImageView = itemView.findViewById(R.id.episode_img)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(info: EpisodesInfo) {
            episodeNameView.text = info.name
            episodeDateView.text = info.airDate
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