package com.romka_po.driveassistant.adapters.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.driveassistant.databinding.ItemBrandBinding
import com.romka_po.driveassistant.model.Brand

class BrandRVAdapter(
    var languageList: Array<Brand>,
) : RecyclerView.Adapter<BrandRVAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: ItemBrandBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // bind the items with each item
    // of the list languageList
    // which than will be
    // shown in recycler view
    // to keep it simple we are
    // not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                binding.brand.text = this.name
                binding.brand.setOnClickListener { onItemClickListener?.let { it(this) } }
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return languageList.size
    }
    private var onItemClickListener: ((Brand) -> Unit)? = null

    fun setOnItemClickListener(listener: (Brand) -> Unit) {
        onItemClickListener = listener
    }

}