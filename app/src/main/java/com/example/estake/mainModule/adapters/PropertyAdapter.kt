package com.example.estake.mainModule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estake.common.models.PropertyModel
import com.example.estake.databinding.ItemPropertyCardBinding

class PropertyAdapter(private val list: List<PropertyModel>) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPropertyCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPropertyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // Bind Data
        holder.binding.tvTitle.text = item.title
        holder.binding.tvRentLabel.text = "Rent: ${item.rentAmount}"
        holder.binding.tvRoiLabel.text = "ROI: ${item.roi}%  |  IRR: ${item.irr}"
        holder.binding.progressBar.progress = item.fundedPercentage

        // If you have a progress text view (e.g. "60%")
        // holder.binding.tvProgressText.text = "${item.fundedPercentage}%"

        // Load Images (Tenant Logo)
        if (item.tenantLogoUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(item.tenantLogoUrl).into(holder.binding.ivTenantLogo)
        }
    }

    override fun getItemCount() = list.size
}