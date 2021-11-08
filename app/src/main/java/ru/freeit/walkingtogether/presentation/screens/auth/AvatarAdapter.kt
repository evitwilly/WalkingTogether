package ru.freeit.walkingtogether.presentation.screens.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.walkingtogether.databinding.AvatarListItemBinding

class AvatarAdapter(
    private val listener: AvatarListItemListener,
    private val drawableResources: List<Int>
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    class AvatarViewHolder(private val binding: AvatarListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(drawableId: Int, listener: AvatarListItemListener) {
            binding.avatarImage.setImageResource(drawableId)
            binding.avatarImage.setOnClickListener {
                listener.onDrawableResource(drawableId)
            }
        }

        companion object {
            fun from(parent: ViewGroup) = AvatarViewHolder(
                AvatarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AvatarViewHolder.from(parent)
    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) = holder.bind(drawableResources[position], listener)
    override fun getItemCount() = drawableResources.size
}