package com.ru.malevich.unscramblegame.load

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.game.ButtonUi
import org.hamcrest.Matcher

class LoadPage {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.loadContainer))
    private val containerClassMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val retryButton = ButtonUi(
        id = R.id.retryButton,
        textResId = R.string.retry,
        containerIdMatcher = containerIdMatcher,
        containerClassMatcher = containerClassMatcher
    )
    private val errorTextView = ErrorUi(
        id = R.id.errorTextView,
        containerIdMatcher = containerIdMatcher,
        containerClassMatcher = containerClassMatcher
    )
    private val progressBar = ProgressUi(
        id = R.id.progressBar,
        containerIdMatcher = containerIdMatcher,
        containerClassMatcher = containerClassMatcher
    )

    fun assertProgressState() {
        retryButton.assertNotVisible()
        errorTextView.assertNotVisible()
        progressBar.assertVisible()
    }

    fun waitTillError() {
        errorTextView.waitTillVisible()
    }

    fun assertErrorState() {
        retryButton.assertVisible()
        errorTextView.assertVisible()
        progressBar.assertNotVisible()
    }

    fun clickRetry() {
        retryButton.click()
    }

    fun waitTillGone() {
        errorTextView.waitTillNotExist()
    }

}
