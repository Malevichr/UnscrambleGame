package com.ru.malevich.unscramblegame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ru.malevich.unscramblegame.game.GamePage
import com.ru.malevich.unscramblegame.gameover.GameOverPage
import com.ru.malevich.unscramblegame.views.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    private lateinit var gamePage: GamePage
    private lateinit var application: UGApp

    private fun doWithRecreate(
        action: () -> Unit
    ) {
        action.invoke()
        scenarioRule.scenario.recreate()
        action.invoke()
    }
    @Before
    fun setup(){
        gamePage = GamePage(scrambledWord = "auto".reversed())
        application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as UGApp

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

    /**
     * UGTC-05
     */
    @Test
    fun caseNumber5() {
        //region 2 incorrect
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input(text = "auau")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertWrongAnsweredState() }

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input("animaa")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertWrongAnsweredState() }

        gamePage.clickSkip()
        gamePage.assertDoesNotExist()
        var gameOverPage = GameOverPage(
            incorrects = 2,
            corrects = 0
        )
        doWithRecreate { gameOverPage.assertInitialState() }

        gameOverPage.clickNewGame()
        gameOverPage.asserDoesNotExist()
        //endregion

        //region 1 incorrect and 1 correct
        gamePage = GamePage(scrambledWord = "auto".reversed())
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input("auto")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertRightAnsweredState() }

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input("animaa")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertWrongAnsweredState() }

        gamePage.clickSkip()

        gamePage.assertDoesNotExist()
        gameOverPage = GameOverPage(
            incorrects = 1,
            corrects = 1
        )
        doWithRecreate { gameOverPage.assertInitialState() }

        gameOverPage.clickNewGame()
        gameOverPage.asserDoesNotExist()
        //endregion

        //region 2 correct
        gamePage = GamePage(scrambledWord = "auto".reversed())
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input("auto")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertRightAnsweredState() }

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "animal".reversed())
        doWithRecreate { gamePage.assertInitialState() }

        gamePage.input("animal")
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertRightAnsweredState() }

        gamePage.clickNext()

        gamePage.assertDoesNotExist()
        gameOverPage = GameOverPage(
            incorrects = 0,
            corrects = 2
        )
        doWithRecreate { gameOverPage.assertInitialState() }
        //endregion
    }
}

