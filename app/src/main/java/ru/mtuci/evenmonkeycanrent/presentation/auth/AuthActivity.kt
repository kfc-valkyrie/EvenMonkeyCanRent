package ru.mtuci.evenmonkeycanrent.presentation.auth

import android.os.Bundle
import ru.mtuci.evenmonkeycanrent.R
import ru.mtuci.evenmonkeycanrent.databinding.ActivityPlaceholderBinding
import ru.mtuci.evenmonkeycanrent.presentation.common.ConnectionAwareActivity
import ru.mtuci.evenmonkeycanrent.presentation.common.applySystemBarsPadding

/** Заглушка экрана входа/регистрации — реализуется в следующих лабораторных работах. */
class AuthActivity : ConnectionAwareActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()
        binding.tvPlaceholder.setText(R.string.auth_placeholder)
    }
}
