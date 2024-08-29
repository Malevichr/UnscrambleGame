package com.ru.malevich.unscramblegame.views.checkbutton

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class CheckButton : AppCompatButton, UpdateCheckButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CheckButtonSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CheckButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    private lateinit var state: CheckUiState
    override fun update(checkUiState: CheckUiState) {
        state = checkUiState
        state.update(this)
    }

    override fun update(clickable: Boolean, enabled: Boolean, color: String) {
        isClickable = clickable
        isEnabled = enabled
        setBackgroundColor(Color.parseColor(color))
    }
}

interface UpdateCheckButton {
    fun update(checkUiState: CheckUiState)
    fun update(
        clickable: Boolean,
        enabled: Boolean,
        color: String
    )
}