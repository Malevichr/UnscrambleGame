package com.ru.malevich.unscramblegame.views.unscrambledwordedittext

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.ru.malevich.unscramblegame.views.scrambledwordtextview.UpdateText

class UnscrambledWordEditText : androidx.appcompat.widget.AppCompatEditText,
    UpdateUnscrambledEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var state: InputUiState
    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = UnscrambledSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as UnscrambledSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(inputUiState: InputUiState) {
        state = inputUiState
        state.update(this)
    }

    override fun update(availabilityToInput: Boolean, clearText: Boolean) {
        isEnabled = availabilityToInput
        if (clearText)
            text?.clear()
    }

    override fun update(text: String) {
        if (text != this.text.toString())
            this.setText(text)
    }

}

interface UpdateUnscrambledEditText : UpdateText {
    fun update(inputUiState: InputUiState)
    fun update(
        availabilityToInput: Boolean,
        clearText: Boolean
    )
}