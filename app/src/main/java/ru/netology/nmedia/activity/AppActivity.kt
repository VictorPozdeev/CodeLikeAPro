package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()){
                Snackbar.make(binding.root, R.string.content_must_not_be_empty, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok){
                        finish()
                    }
                    .show()
            } else {
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = text
                }
            )
        }
    }
}