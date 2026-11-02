package ru.mtuci.evenmonkeycanrent.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.mtuci.evenmonkeycanrent.EvenMonkeyCanRentApp
import ru.mtuci.evenmonkeycanrent.di.AppContainer

/** Фабрика вьюмоделей, получающая зависимости из [AppContainer]. */
inline fun <reified VM : ViewModel> appViewModelFactory(
    crossinline create: (AppContainer) -> VM
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val app = this[APPLICATION_KEY] as EvenMonkeyCanRentApp
        create(app.container)
    }
}
