package com.ru.malevich.unscramblegame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ru.malevich.unscramblegame.game.GamePage
import com.ru.malevich.unscramblegame.views.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    private lateinit var gamePage: GamePage
    private lateinit var application: FakeApp
    @Before
    fun setup(){
        gamePage = GamePage(scrambledWord = "auto".reversed())
        application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as FakeApp
        application.resetViewModel()
    }

    @After
    fun tearDown() {
        application.resetViewModel()

    }
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * UGTC-01
     */
    @Test
    fun caseNumber1() {

        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()


        gamePage.remove(count = 2)
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()
        scenarioRule.scenario.recreate()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()


    }

    /**
     * UGTC-02
     */
    @Test
    fun caseNumber2(){
        application.resetViewModel()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "au")
        gamePage.assertSufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()
        scenarioRule.scenario.recreate()
        gamePage.assertWrongAnsweredState()

        gamePage.remove(count = 2)
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertRightAnsweredState()
        scenarioRule.scenario.recreate()
        gamePage.assertRightAnsweredState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

    }

    /**
     * UGTC-03
     */
    @Test
    fun caseNumber3(){
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "to")
        gamePage.assertSufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertSufficientInputState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

    }

    /**
     * UGTC-04
     */
    @Test
    fun caseNumber4(){
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.input(text = "au")
        gamePage.assertInsufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertInsufficientInputState()

        gamePage.input(text = "au")
        gamePage.assertSufficientInputState()
        scenarioRule.scenario.recreate()
        gamePage.assertSufficientInputState()

        gamePage.clickCheck()
        gamePage.assertWrongAnsweredState()
        scenarioRule.scenario.recreate()
        gamePage.assertWrongAnsweredState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        gamePage.assertInitialState()
        scenarioRule.scenario.recreate()
        gamePage.assertInitialState()

    }
}

