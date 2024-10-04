package com.ru.malevich.unscramblegame.views.errortext

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ErrorTextView : AppCompatTextView, UpdateError {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var state: ErrorUiState
    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = ErrorSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ErrorSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(errorUiState: ErrorUiState) {
        state = errorUiState
        state.update(this)
    }

    override fun updateVisibility(visibility: Int) {
        this.visibility = visibility
    }


    override fun updateTextResId(id: Int) {
        setText(id)
    }
}

interface UpdateError {
    fun updateVisibility(visibility: Int)
    fun updateTextResId(id: Int)
    fun update(errorUiState: ErrorUiState)
}