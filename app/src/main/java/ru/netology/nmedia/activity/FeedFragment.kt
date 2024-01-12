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
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val interactionListener = object : OnInteractionListener {
        override fun like(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun share(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.share))
            startActivity(shareIntent)
            viewModel.shareById(post.id)
        }

        override fun watch(post: Post) {
            val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
            startActivity(intentVideo)
        }

        override fun remove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun edit(post: Post) {
            viewModel.edit(post)
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = post.content
                })
        }

        override fun watchPost(post: Post) {
            findNavController().navigate(
                R.id.action_feedFragment_to_postFragment,
                Bundle().apply {
                    textArg = post.id.toString()
                })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostAdapter(interactionListener)

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.addPostButton.setOnClickListener {
            viewModel.resetEditingState()
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}
