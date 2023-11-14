package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.dto.correctDisplayOfNumbers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                date.text = post.published
                content.text = post.content

                ibFavorite.setImageResource(
                    if(post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )

                numberOfLikes.text = correctDisplayOfNumbers(post.countLiked)
                numberOfShare.text = correctDisplayOfNumbers(post.countShare)
                numberOfViews.text = correctDisplayOfNumbers(post.counterView)

            }
        }

        binding.ibFavorite.setOnClickListener {
            viewModel.like()
        }

        binding.ibShare.setOnClickListener {
            viewModel.share()
        }
    }


}
