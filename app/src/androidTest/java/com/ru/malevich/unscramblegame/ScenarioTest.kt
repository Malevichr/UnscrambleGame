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
        gamePage = GamePage(scrambledWord = "emag", unscrambledWord = "game")
    }
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * UGTC-01
     */
    @Test
    fun caseNumber1() {
        gamePage.assertInitialState()

        gamePage.type("ga")
        gamePage.assertInsufficientInputState()

        gamePage.erase(2)
        gamePage.assertInitialState()

        gamePage.type("ga")
        gamePage.assertInsufficientInputState()

        gamePage.type("me")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "drow", unscrambledWord = "word")
        gamePage.assertInitialState()
    }

    /**
     * UGTC-02
     */
    @Test
    fun caseNumber2(){
        gamePage.assertInitialState()

        gamePage.type("ga")
        gamePage.assertInsufficientInputState()

        gamePage.erase("ga")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()

        gamePage.erase(2)
        gamePage.assertInsufficientInputState()

        gamePage.type("me")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "drow", unscrambledWord = "word")
        gamePage.assertInitialState()
    }

    /**
     * UGTC-03
     */
    fun caseNumber3(){
        gamePage.assertInitialState()

        gamePage.type("ga")
        gamePage.assertInsufficientInputState()

        gamePage.type("me")
        gamePage.assertSufficientInputState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "drow", unscrambledWord = "word")
        gamePage.assertInitialState()
    }

    /**
     * UGTC-04
     */
    fun caseNumber4(){
        gamePage.assertInitialState()

        gamePage.type("ga")
        gamePage.assertInsufficientInputState()

        gamePage.erase("ga")
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "drow", unscrambledWord = "word")
        gamePage.assertInitialState()
    }
}