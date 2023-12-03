package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.truncateText
import ru.netology.nmedia.util.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel by viewModels<PostViewModel>()
        val adapter = PostAdapter(object: OnInteractionListener {
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun edit(post: Post) {
                viewModel.edit(post)
            }
            override fun share(post: Post) {
                viewModel.shareById(post.id)
            }
        })
        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this){
            if (it.id == 0L){
                binding.wgEditCancel.visibility = View.GONE
                binding.wgEditCancel.visibility = View.INVISIBLE
            } else {
                binding.etContent.setText(it.content)
                binding.etContent.requestFocus()
                binding.wgEditCancel.visibility = View.VISIBLE
                AndroidUtils.showTheKeyboardNow(binding.etContent)
                binding.tvPostText.text = truncateText(it.content)
            }
        }

        binding.ibSave.setOnClickListener {
            with(binding.etContent){
                val content = text.toString()
                if (content.isNullOrBlank()){
                    Toast.makeText(this@MainActivity,
                        R.string.content_must_not_be_empty,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(content)
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.ibCancel.setOnClickListener {
            viewModel.resetEditingState()
            with(binding.etContent){
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.wgEditCancel.visibility = View.GONE
            }
        }
    }
}
