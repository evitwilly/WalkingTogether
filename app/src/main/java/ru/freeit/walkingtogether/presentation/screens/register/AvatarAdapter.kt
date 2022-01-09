package ru.freeit.walkingtogether.presentation.screens.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.walkingtogether.core.extensions.click
import ru.freeit.walkingtogether.databinding.AvatarListItemBinding

class AvatarAdapter(
    private val listener: AvatarListItemListener,
    private val avatars: List<AvatarImage>
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    class AvatarViewHolder(private val binding: AvatarListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(avatar: AvatarImage, listener: AvatarListItemListener) {
            avatar.img(binding.avatarImage)
            binding.avatarImage.click { listener.onAvatar(avatar) }
        }

        companion object {
            fun from(parent: ViewGroup) = AvatarViewHolder(AvatarListItemBinding.inflate(LayoutInflater
                .from(parent.context), parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AvatarViewHolder.from(parent)
    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) = holder.bind(avatars[position], listener)
    override fun getItemCount() = avatars.size
}