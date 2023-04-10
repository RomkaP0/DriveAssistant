package com.romka_po.driveassistant.adapters.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.driveassistant.databinding.CardCarBinding
import com.romka_po.driveassistant.model.Model


class ModelRVAdapter : RecyclerView.Adapter<ModelRVAdapter.ModelViewHolder>() {

    inner class ModelViewHolder(val binding: CardCarBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder(
            CardCarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val model = differ.currentList[position]

        holder.binding.apply {
            textView4.text = model.name

        }
    }

    override fun getItemCount(): Int {
        // this method returns the size of recyclerview
        return differ.currentList.size
    }

    // View Holder Class to handle Recycler View.
}