package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringProperty
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        arguments?.textArg?.let {
            binding.content.setText(it)
        }

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.content.requestFocus()
        binding.OkButton.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringProperty
    }
}


