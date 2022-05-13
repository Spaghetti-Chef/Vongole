package org.sopt.spaghettichef.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.spaghettichef.databinding.ItemFriendBinding
import org.sopt.spaghettichef.model.db.friend.FriendInfo

class FriendListAdapter(
    private val clickListener: (FriendInfo) -> Unit,
) :
    ListAdapter<FriendInfo, FriendListAdapter.FriendHorizontalViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHorizontalViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendHorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendHorizontalViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data, clickListener)
    }

    class FriendHorizontalViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            friend: FriendInfo,
            clickListener: (FriendInfo) -> Unit,
        ) {
            binding.friendInfo = friend
            binding.friendContainer.apply {
                setOnClickListener { clickListener(friend) }
            }
        }
    }

    companion object {
        private const val TAG = "FriendListAdapter"

        private val diffCallback = object : DiffUtil.ItemCallback<FriendInfo>() {
            override fun areItemsTheSame(oldItem: FriendInfo, newItem: FriendInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FriendInfo, newItem: FriendInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}