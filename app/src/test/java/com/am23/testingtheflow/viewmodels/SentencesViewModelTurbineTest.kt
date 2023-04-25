package com.am23.testingtheflow.viewmodels

import app.cash.turbine.test
import com.am23.testingtheflow.usecases.FetchSentencesUseCase
import com.am23.testingtheflow.usecases.FetchSentencesWithDelayUseCase
import com.am23.testingtheflow.utils.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class SentencesViewModelTurbineTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val sentencesViewModel = SentencesViewModel(
        FetchSentencesUseCase(dispatcher = mainDispatcherRule.testDispatcher),
        FetchSentencesWithDelayUseCase(dispatcher = mainDispatcherRule.testDispatcher)
    )

    @Test
    fun `should change state from loading to most recent sentence in turbine`() = runTest {
        val expected = "Greetings, from AM23"

        sentencesViewModel.sentencesState.test {
            sentencesViewModel.fetchSentences()

            awaitItem() shouldBeEqualTo "Loading"

            delay(100.milliseconds)
            expectMostRecentItem() shouldBeEqualTo expected

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `fetch all sentences in order in turbine`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        sentencesViewModel.sentencesState.test {
            sentencesViewModel.fetchSentences()

            awaitItem() shouldBeEqualTo "Loading"
            awaitItem() shouldBeEqualTo expected[0]
            awaitItem() shouldBeEqualTo expected[1]
            awaitItem() shouldBeEqualTo expected[2]

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should change state from loading to most recent sentence with delay in turbine`() = runTest {
        val expected = "Greetings, from AM23"

        sentencesViewModel.sentencesState.test {
            sentencesViewModel.fetchSentences()

            awaitItem() shouldBeEqualTo "Loading"

            delay(100.milliseconds)
            expectMostRecentItem() shouldBeEqualTo expected

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `fetch all sentences in order with delay in turbine`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        sentencesViewModel.sentencesDelayState.test {
            sentencesViewModel.fetchSentencesWithDelay()

            awaitItem() shouldBeEqualTo "Loading"
            awaitItem() shouldBeEqualTo expected[0]
            awaitItem() shouldBeEqualTo expected[1]
            awaitItem() shouldBeEqualTo expected[2]

            ensureAllEventsConsumed()
        }
    }
}