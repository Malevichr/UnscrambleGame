package com.ru.malevich.unscramblegame.views.statstextview

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ru.malevich.unscramblegame.R
import java.io.Serializable

class StatsTextView : AppCompatTextView, UpdateStats {
    private lateinit var state: StatsUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = StatsSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as StatsSavedState
        super.onRestoreInstanceState(restoredState.superState)
        updateState(restoredState.restore())
    }

    override fun getFreezesText() = true
    override fun updateState(statsUiState: StatsUiState) {
        state = statsUiState
        state.update(this)
    }

    override fun updateUi(corrects: Int, incorrects: Int) {
        text = resources.getString(R.string.stats, corrects, incorrects)
    }
}

interface StatsUiState : Serializable {
    object Empty : StatsUiState {
        override fun update(textView: UpdateStats) = Unit

    }

    fun update(textView: UpdateStats)
    data class Base(private val corrects: Int, private val incorrects: Int) : StatsUiState {
        override fun update(textView: UpdateStats) {
            textView.updateUi(corrects, incorrects)
        }
    }
}

interface UpdateStats {
    fun updateState(statsUiState: StatsUiState)
    fun updateUi(corrects: Int, incorrects: Int)
}