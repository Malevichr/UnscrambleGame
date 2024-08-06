package com.ru.malevich.unscramblegame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    private lateinit var gamePage: GamePage
    @Before
    fun setup(){
        gamePage = GamePage(scrambledWord = "auto".reversed())
    }
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * UGTC-01
     */
    @Test
    fun caseNumber1() {
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()

        gamePage.remove(count = 2)
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
    }

    /**
     * UGTC-02
     */
    @Test
    fun caseNumber2(){
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "au")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()

        gamePage.remove(count = 2)
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
    }

    /**
     * UGTC-03
     */
    fun caseNumber3(){
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
    }

    /**
     * UGTC-04
     */
    fun caseNumber4(){
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "au")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
    }
}