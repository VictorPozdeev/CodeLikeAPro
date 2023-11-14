package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
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
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe, countLiked = if(post.likedByMe) post.countLiked - 1 else post.countLiked +1)
        data.value = post
    }

    override fun share() {
        post = post.copy(countShare = post.countShare + 1)
        data.value = post
    }
}
