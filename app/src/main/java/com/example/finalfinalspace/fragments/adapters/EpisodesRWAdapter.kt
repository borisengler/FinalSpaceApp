package com.example.finalfinalspace.fragments.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo

class EpisodesRWAdapter(context: Context, episodesData: List<EpisodesInfo>)
    : RecyclerView.Adapter<EpisodesRWAdapter.EpisodeViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onListener: OnItemClickListener
    private val ctx: Context = context
    private val episodes: List<EpisodesInfo> = episodesData

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesRWAdapter.EpisodeViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = inflater.inflate(R.layout.episode_cardview, parent, false)

        return EpisodeViewHolder(view, onListener)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.episodeNameView.text = episodes[position].name
        holder.episodeDateView.text = episodes[position].airDate
        val epIdStr = (position+1).toString()
        var path = ctx.getExternalFilesDir(null).toString() + "/images/image$epIdStr.jpg"
        val bitmap = BitmapFactory.decodeFile(path)
        holder.episodeImage.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    inner class EpisodeViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var episodeNameView: TextView = itemView.findViewById(R.id.episode_name)
        var episodeDateView: TextView = itemView.findViewById(R.id.episode_date)
        var episodeImage: ImageView = itemView.findViewById(R.id.episode_img)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        onListener = listener
    }

}