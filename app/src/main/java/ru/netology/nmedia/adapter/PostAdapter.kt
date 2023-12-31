package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.correctDisplayOfNumbers

interface OnInteractionListener {
    fun like(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
    fun share(post: Post)
    fun watch(post: Post)
}

class PostAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            date.text = post.published
            content.text = post.content

            mbFavorite.setOnClickListener {
                onInteractionListener.like(post)
            }

            mbShare.setOnClickListener {
                onInteractionListener.share(post)
            }

            videoView.setOnClickListener {
                onInteractionListener.watch(post)
            }

            if (post.videoUrl != null) {
                postVideoGroup.visibility = View.VISIBLE
            } else {
                postVideoGroup.visibility = View.GONE
            }

            mbFavorite.text = correctDisplayOfNumbers(post.countLiked)
            mbShare.text = correctDisplayOfNumbers(post.countShare)
            numberOfViews.text = correctDisplayOfNumbers(post.counterView)

            bMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.remove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.edit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            mbFavorite.isChecked = post.likedByMe
            mbFavorite.text = correctDisplayOfNumbers(post.countLiked)
            mbShare.text = correctDisplayOfNumbers(post.countShare)
            numberOfViews.text = correctDisplayOfNumbers(post.counterView)
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}