package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostBinding.inflate(layoutInflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val listener = object : OnInteractionListener {
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun share(post: Post) {
                viewModel.shareById(post.id)
                val intentText = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val startIntent = Intent.createChooser(intentText, getString(R.string.share))
                startActivity(startIntent)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun edit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_postFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun watch(post: Post) {
                val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                startActivity(intentVideo)
            }

            override fun watchPost(post: Post) {}
        }

        val currentPostId = requireArguments().textArg!!.toLong()

        binding.postWatch.apply {
            viewModel.data.observe(viewLifecycleOwner) { it ->
                val viewHolder = PostViewHolder(binding.postWatch, listener)
                val post = it.find { it.id == currentPostId }
                post?.let { viewHolder.bind(post) }
            }
        }
        return binding.root
    }
}