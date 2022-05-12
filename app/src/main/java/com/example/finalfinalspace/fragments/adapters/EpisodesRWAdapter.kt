package com.example.finalfinalspace.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo

class EpisodesRWAdapter(context: Context, episodesData: List<EpisodesInfo>)
    : RecyclerView.Adapter<EpisodesRWAdapter.EpisodeViewHolder>() {



    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onListener: onItemClickListener
    private val ctx: Context = context
    private val episodes: List<EpisodesInfo> = episodesData

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesRWAdapter.EpisodeViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = inflater.inflate(R.layout.episode_cardview, parent, false)

        return EpisodeViewHolder(view, onListener);
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.episodeNameView.text = episodes.get(position).name
        holder.episodeDateView.text = episodes.get(position).airDate
        holder.episodeSrcView.text = episodes.get(position).imageUrl
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    inner class EpisodeViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var episodeNameView: TextView
        var episodeDateView: TextView
        var episodeSrcView: TextView

        init {
            episodeNameView = itemView.findViewById(R.id.episode_name)
            episodeDateView = itemView.findViewById(R.id.episode_date)
            episodeSrcView = itemView.findViewById(R.id.episode_img_src)
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setOnClickListener(listener: onItemClickListener) {
        onListener = listener
    }

}