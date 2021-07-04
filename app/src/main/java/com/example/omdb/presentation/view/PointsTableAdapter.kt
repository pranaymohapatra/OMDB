package com.starwars.starwarstournament.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.starwars.starwarstournament.R
import com.starwars.starwarstournament.common.dto.pointstable.PointsItem
import com.starwars.starwarstournament.databinding.ItemPointsTableBinding

class PointsTableAdapter(private val itemClickHandler: (Int) -> Unit) :
    ListAdapter<PointsItem, PointsTableViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsTableViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_points_table, parent, false)
        return PointsTableViewHolder(ItemPointsTableBinding.bind(itemView)).apply {
            itemView.setOnClickListener {
                run {
                    itemClickHandler.invoke(getItem(itemPos).id)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PointsTableViewHolder, position: Int) {
        holder.apply {
            itemPos = position
            bind(getItem(position))
        }
    }

}

class PointsTableViewHolder(private val itemPointsTableBinding: ItemPointsTableBinding) :
    RecyclerView.ViewHolder(itemPointsTableBinding.root) {
    var itemPos: Int = -1
    fun bind(item: PointsItem) {
        item.icon.let {
            Glide.with(itemView.context).load(it)
                .placeholder(R.drawable.light_saber)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemPointsTableBinding.playerIcon)
        }
        itemPointsTableBinding.name.text = item.name
        itemPointsTableBinding.points.text = item.points.toString()
    }
}

val diffUtil = object : DiffUtil.ItemCallback<PointsItem>() {
    override fun areItemsTheSame(oldItem: PointsItem, newItem: PointsItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PointsItem, newItem: PointsItem) =
        oldItem == newItem
}
