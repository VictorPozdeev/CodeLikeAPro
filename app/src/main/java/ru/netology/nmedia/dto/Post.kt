package ru.netology.nmedia.dto

import kotlin.math.floor

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val countLiked: Int,
    val countShare: Int,
    val counterView: Int
)

 internal fun correctDisplayOfNumbers(num: Int): String {
    return when (num) {
        in 0..999 -> num.toString()
        in 1_000..9_999 -> if (num % 1_000 == 0) {
            "${floor(num / 1_000.0).toInt()}K"
        } else if ((num / 100) % 10 == 0) {
            "${floor(num / 1_000.0).toInt()}K"
        } else {
            "${(floor(num / 100.0) / 10.0)}K"
        }

        in 10_000..999_999 -> "${floor(num / 1000.0).toInt()}K"
        in 1_000_000..999_999_999 -> if (num % 1_000_000 == 0) {
            "${floor(num / 1_000_000.0).toInt()}M"
        } else if ((num / 100_000) % 10 == 0) {
            "${floor(num / 1_000_000.0).toInt()}M"
        } else {
            "${floor(num / 100_000.0) / 10.0}M"
        }

        else -> "0"
    }
}

internal fun truncateText(text: String, maxLength: Int): String {
    if (text.length <= maxLength) return text
    return text.substring(0, maxLength) + "..."
}



