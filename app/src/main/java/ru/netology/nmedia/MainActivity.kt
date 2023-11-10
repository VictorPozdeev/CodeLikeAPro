package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "\uD83D\uDCA1 Если вы в детстве лучше всех решали задачки со звёздочкой, побеждали в конкурсе на скорочтение или могли собрать кубик Рубика за пару минут — скорее всего, вы прирождённый тренер по майнд-фитнесу.\n" +
                    "Чтобы узнать, что такое ментальная гимнастика, и как нейрофизиология влияет на наши навыки, приходите 7 ноября на бесплатное занятие «Профессия будущего: как тренировать мозг и зарабатывать на этом». Вы:\n" +
                    "\uD83D\uDD39Поймёте, как работает ваш мозг и научитесь его тренировать.\n" +
                    "\uD83D\uDD39Сможете помогать мозгу лучше справляться с трудностями и решать задачи эффективнее.\n" +
                    "\uD83D\uDD39Узнаете, как тренировать других и зарабатывать на этом.\n" +
                    "→ https://netology.ru/",
            published = "4 ноября в 18:36",
            likedByMe = false,
            countLiked = 999,
            countShare = 1_099,
            counterView = 400_099_999
        )

        with(binding) {
            author.text = post.author
            date.text = post.published
            content.text = post.content

            numberOfLikes.text = correctDisplayOfNumbers(post.countLiked)
            numberOfShare.text = correctDisplayOfNumbers(post.countShare)
            numberOfViews.text = correctDisplayOfNumbers(post.counterView)

            if (post.likedByMe) {
                ibFavorite.setImageResource(R.drawable.baseline_favorite_24)
            }

            ibFavorite.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    ibFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    post.countLiked++
                    numberOfLikes.text = correctDisplayOfNumbers(post.countLiked)
                } else {
                    ibFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    post.countLiked--
                    numberOfLikes.text = correctDisplayOfNumbers(post.countLiked)
                }
            }

            ibShare.setOnClickListener {
                post.countShare++
                numberOfShare.text = correctDisplayOfNumbers(post.countShare)
            }
        }

//        binding.root.setOnClickListener{
//            println("binding.root.setOnClickListener")
//        }
//        binding.ibFavorite.setOnClickListener {
//            println("binding.ibFavorite.setOnClickListener")
//        }
//        binding.ivLogo.setOnClickListener {
//            println("binding.ivLogo.setOnClickListener")
//        }

    }

    private fun correctDisplayOfNumbers(num: Int): String {
        return when(num) {
            in 0..999 -> num.toString()
            in 1_000..9_999 -> if(num % 1_000 == 0) {
                "${floor(num / 1_000.0).toInt()}K"
            } else if ((num / 100) % 10 == 0){
                "${floor(num / 1_000.0).toInt()}K"
            } else {
                "${(floor(num / 100.0) / 10.0)}K"
            }
            in 10_000..999_999 -> "${floor(num / 1000.0).toInt()}K"
            in 1_000_000..999_999_999 -> if(num % 1_000_000 == 0) {
                "${floor(num / 1_000_000.0).toInt()}M"
            } else if((num / 100_000) % 10 == 0){
                "${floor(num / 1_000_000.0).toInt()}M"
            } else {
                "${floor(num / 100_000.0) / 10.0}M"
            }
            else -> "0"
        }
    }
}