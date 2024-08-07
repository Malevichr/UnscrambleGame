package com.ru.malevich.unscramblegame.game

import android.view.KeyEvent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ru.malevich.unscramblegame.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class InputTextUi(
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) {
    private val inputLayoutId: Int = R.id.inputLayout
    private val inputLayoutInteraction: ViewInteraction = onView(
        allOf(
            containerClassIdMatcher,
            containerIdMatcher,
            withId(inputLayoutId),
            isAssignableFrom(TextInputLayout::class.java)
        )
    )
    private val inputInteraction: ViewInteraction = onView(
        allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDisplayed(),
            withId(R.id.inputText),
            withParent(
                allOf(
                    isAssignableFrom(TextInputEditText::class.java),
                    withId(inputLayoutId)
                )
            )
        )
    )

    fun assertInitialState() {
        inputLayoutInteraction.check(matches(isEnabled()))
        inputInteraction.check(matches(withText("")))
    }

    fun input(text: String) {
        inputInteraction.perform(typeText(text), closeSoftKeyboard())
    }

    fun assertInsufficientInputState() {
        inputLayoutInteraction.check(matches(isEnabled()))
    }

    fun remove(count: Int) {
        for (i in 1..count)
            inputInteraction.perform(
                click(),
                pressKey(KeyEvent.KEYCODE_DEL),
                closeSoftKeyboard()
            )
    }

    fun assertSufficientInputState() {
        inputLayoutInteraction.check(matches(isEnabled()))
    }

    fun assertNotAvailableToInput() {
        inputLayoutInteraction.check(matches(isNotEnabled()))
    }

}
