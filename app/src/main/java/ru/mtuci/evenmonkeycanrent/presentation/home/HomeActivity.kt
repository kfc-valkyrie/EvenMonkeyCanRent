package ru.mtuci.evenmonkeycanrent.presentation.home

import android.os.Bundle
import ru.mtuci.evenmonkeycanrent.R
import ru.mtuci.evenmonkeycanrent.databinding.ActivityPlaceholderBinding
import ru.mtuci.evenmonkeycanrent.presentation.common.ConnectionAwareActivity
import ru.mtuci.evenmonkeycanrent.presentation.common.applySystemBarsPadding

/** Заглушка главного экрана — реализуется в следующих лабораторных работах. */
class HomeActivity : ConnectionAwareActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()
        binding.tvPlaceholder.setText(R.string.home_placeholder)
    }
}
