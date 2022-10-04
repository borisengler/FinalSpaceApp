package com.example.finalfinalspace.ui.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.databinding.ItemEmptyBinding
import javax.inject.Inject

class EmptyHeaderAdapter @Inject constructor() : RecyclerView.Adapter<EmptyHeaderAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemEmptyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit
}
