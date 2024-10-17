package com.ru.malevich.unscramblegame.views.scrambledwordtextview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScrambledTextView : AppCompatTextView, UpdateText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getFreezesText(): Boolean = true
    override fun updateText(text: String) {
        this.text = text
    }

    override fun updateTextResId(id: Int) {
        setText(id)
    }

}

interface UpdateText {
    fun updateText(text: String)
    fun updateTextResId(id: Int)
}