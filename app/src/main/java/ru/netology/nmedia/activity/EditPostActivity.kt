package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentEdit.setText(intent?.getStringExtra(Intent.EXTRA_TEXT))

        binding.OkButton.setOnClickListener {
            val text = binding.contentEdit.text.toString()
            if (text.isBlank()) {
                setResult(Activity.RESULT_CANCELED)
            } else {
                setResult(Activity.RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            finish()
        }
    }

    object ContractEdit : ActivityResultContract<String, String?>() {
        override fun createIntent(context: Context, input: String) =
            Intent(context, EditPostActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            intent?.getStringExtra(Intent.EXTRA_TEXT)
    }
}
