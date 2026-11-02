package ru.mtuci.evenmonkeycanrent.presentation.common

import android.content.Context
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.mtuci.evenmonkeycanrent.R

/**
 * Добавляет к исходным отступам view отступы системных панелей и выреза экрана,
 * чтобы контент не уходил под статус-бар и панель навигации (edge-to-edge).
 */
fun View.applySystemBarsPadding() {
    val initialLeft = paddingLeft
    val initialTop = paddingTop
    val initialRight = paddingRight
    val initialBottom = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.updatePadding(
            left = initialLeft + bars.left,
            top = initialTop + bars.top,
            right = initialRight + bars.right,
            bottom = initialBottom + bars.bottom
        )
        insets
    }
}

/** Диалог с текстом ошибки (в том числе ошибки, полученной от сервера). */
fun Context.showErrorDialog(message: String?, onDismiss: () -> Unit = {}) {
    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.error_title)
        .setMessage(message ?: getString(R.string.error_unknown))
        .setPositiveButton(R.string.ok, null)
        .setOnDismissListener { onDismiss() }
        .show()
}
