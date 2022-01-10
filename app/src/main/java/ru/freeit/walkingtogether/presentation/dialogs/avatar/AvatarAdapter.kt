package ru.freeit.walkingtogether.presentation.dialogs.avatar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.walkingtogether.core.extensions.click
import ru.freeit.walkingtogether.databinding.AvatarListItemBinding
import ru.freeit.walkingtogether.presentation.base.AvatarImage

class AvatarAdapter(
    private val avatars: List<AvatarImage>,
    private val listener: AvatarListItemListener,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AvatarViewHolder.from(parent)
    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) = holder.bind(avatars[position], listener)
    override fun getItemCount() = avatars.size
}