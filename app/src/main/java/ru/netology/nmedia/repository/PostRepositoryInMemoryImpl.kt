package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 0L

    private var posts = listOf(
        Post(
            id = nextId++,
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
            counterView = 400_099_999,
            videoUrl = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Большинство выбирает свою карьерную дорогу сразу после школы и двигается по ней без всякого навигатора. Но этот путь, как и любой другой, требует дальнейшей корректировки и доработки.\n" +
                    "Предлагаем разобраться с этой задачей в любое удобное для вас время на бесплатном курсе «Постройте план развития карьеры», где вы:\n" +
                    "\uD83D\uDD39проанализируете собственный опыт;\n" +
                    "\uD83D\uDD39сопоставите свои цели с ситуацией на рынке;\n" +
                    "\uD83D\uDD39поймёте, для чего нужна стратегия и почему это важно, даже если вы не любите планировать;\n" +
                    "\uD83D\uDD39определите, какие навыки и ресурсы вам потребуются, чтобы прийти к желаемому;\n" +
                    "\uD83D\uDD39почувствуете себя увереннее и снизите тревогу.\n" +
                    "Сделать осознанный выбор и идти к цели не сворачивая → https://netolo.gy/clUI",
            published = "5 ноября в 12:10",
            likedByMe = true,
            countLiked = 999,
            countShare = 1_099,
            counterView = 400_099_999,
            videoUrl = "https://rutube.ru/video/c7ee6f719a0256843d298a912d7dbb5d/"
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(
                    likedByMe = !it.likedByMe,
                    countLiked = if(it.likedByMe) it.countLiked - 1 else it.countLiked +1
                )
            } else {
                it
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(countShare = post.countShare + 1)
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        data.value = posts
    }
}