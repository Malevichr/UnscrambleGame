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
import com.google.android.material.textfield.TextInputLayout
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.views.unscrambledwordedittext.UnscrambledWordEditText
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
            isAssignableFrom(UnscrambledWordEditText::class.java),
            isDisplayed(),
            withId(R.id.inputText),
            withParent(
                withParent(
                    allOf(
                        withId(inputLayoutId),
                        isAssignableFrom(TextInputLayout::class.java)
                    )
                )
            )
        )
    )

    fun assertInitialState() {
        inputLayoutInteraction.check(matches(isDisplayed()))
        inputInteraction.check(matches(isEnabled()))
        inputInteraction.check(matches(withText("")))
    }

    fun input(text: String) {
        inputLayoutInteraction.check(matches(isDisplayed()))
        inputInteraction.perform(typeText(text), closeSoftKeyboard())
    }

    fun assertInsufficientInputState() {
        inputLayoutInteraction.check(matches(isDisplayed()))
        inputInteraction.check(matches(isEnabled()))
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
        inputLayoutInteraction.check(matches(isDisplayed()))
        inputInteraction.check(matches(isEnabled()))
    }

    fun assertNotAvailableToInput() {
        inputLayoutInteraction.check(matches(isDisplayed()))
        inputInteraction.check(matches(isNotEnabled()))
    }

}
