package ru.mtuci.evenmonkeycanrent.domain.model

/** Экран, на который нужно перейти после экрана загрузки. */
enum class StartDestination {
    /** Первый запуск — экран приветствия. */
    ONBOARDING,

    /** Токена нет или он недействителен — экран входа. */
    AUTH,

    /** Пользователь авторизован — главный экран. */
    HOME
}
