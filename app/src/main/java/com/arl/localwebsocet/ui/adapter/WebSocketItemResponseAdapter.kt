package com.arl.localwebsocet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arl.localwebsocet.databinding.WebsocketItemListBinding
import com.arl.localwebsocet.model.WebSocketItemResponse
import com.arl.localwebsocet.util.setImageUrl

/**
 * Created by AralBenli on 10.09.2024.
 */
class WebSocketItemResponseAdapter :
    ListAdapter<WebSocketItemResponse, WebSocketItemResponseAdapter.WebSocketItemResponseViewHolder>(
        DiffCallback()
    ) {

    class WebSocketItemResponseViewHolder(private val binding: WebsocketItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: WebSocketItemResponse) {
            binding.tvId.text = response.id.toString()
            binding.tvDescription.text = response.description
            binding.tvTitle.text = response.title
            binding.switchStatus.isChecked = response.booleanStatus
            binding.ivImage.setImageUrl(response.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebSocketItemResponseViewHolder {
        val binding = WebsocketItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WebSocketItemResponseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebSocketItemResponseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<WebSocketItemResponse>() {
        override fun areItemsTheSame(oldItem: WebSocketItemResponse, newItem: WebSocketItemResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WebSocketItemResponse, newItem: WebSocketItemResponse): Boolean {
            return oldItem == newItem
        }
    }
}
