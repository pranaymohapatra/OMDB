package com.example.omdb.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.common.SearchItem
import com.example.omdb.databinding.ItemSearchDetailsBinding

class MatchDetailsAdapter : PagedListAdapter<SearchItem, SearchViewHolder>(searchDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_details, parent, false)
        return SearchViewHolder(ItemSearchDetailsBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply {
            itemPos = position
            getItem(position)?.apply {
                bind(this)
            }
        }
    }

}

class SearchViewHolder(private val viewBinding: ItemSearchDetailsBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var itemPos: Int = -1
    fun bind(item: SearchItem) {
        viewBinding.apply {
            //Add code to bind to view items like this using viewbnding object
//            player1Tv.text = item.player1Name
//            player1PointsTv.text = item.player1Score.toString()
//            player2Tv.text = item.player2Name
//            player2PointsTv.text = item.player2Score.toString()
        }
    }
}

val searchDiffUtil = object : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
        oldItem.imdbID == newItem.imdbID

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
        oldItem == newItem
}
