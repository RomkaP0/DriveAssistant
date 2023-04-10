package com.romka_po.driveassistant.adapters.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.driveassistant.databinding.CardcheckBinding
import com.romka_po.driveassistant.model.Check
import kotlin.math.roundToInt


class CheckRVAdapter : RecyclerView.Adapter<CheckRVAdapter.CheckViewHolder>() {

    inner class CheckViewHolder(val binding: CardcheckBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Check>() {
        override fun areItemsTheSame(oldItem: Check, newItem: Check): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Check, newItem: Check): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        return CheckViewHolder(
            CardcheckBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        val check = differ.currentList[position]

        holder.binding.apply {
            rvcategory.text = check.name
            rvimage.setImageResource(check.image)
            rvdistance.text = check.start.roundToInt().toString()
            rvmax.text = check.end.roundToInt().toString()
        }
    }

    override fun getItemCount(): Int {
        // this method returns the size of recyclerview
        return differ.currentList.size
    }

    // View Holder Class to handle Recycler View.
}