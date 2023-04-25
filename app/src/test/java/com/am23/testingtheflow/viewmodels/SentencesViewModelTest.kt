package com.am23.testingtheflow.viewmodels

import com.am23.testingtheflow.usecases.FetchSentencesUseCase
import com.am23.testingtheflow.usecases.FetchSentencesWithDelayUseCase
import com.am23.testingtheflow.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SentencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val sentencesViewModel = SentencesViewModel(
        FetchSentencesUseCase(dispatcher = mainDispatcherRule.testDispatcher),
        FetchSentencesWithDelayUseCase(dispatcher = mainDispatcherRule.testDispatcher)
    )

    @Test
    fun `should change state from loading to most recent sentence`() = runTest {
        val expected = "Greetings, from AM23"

        val initialSentence = sentencesViewModel.sentencesState.value
        initialSentence shouldBeEqualTo "Loading"

        sentencesViewModel.fetchSentences()
        runCurrent()

        val mostRecentSentence = sentencesViewModel.sentencesState.value
        mostRecentSentence shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")
        val sentences = mutableListOf<String>()

        sentencesViewModel.sentencesState
            .onEach { sentences.add(it) }
            .flowOn(UnconfinedTestDispatcher(testScheduler))
            .launchIn(backgroundScope)

        sentencesViewModel.fetchSentences()
        advanceUntilIdle()

        sentences[0] shouldBeEqualTo "Loading"
        sentences[1] shouldBeEqualTo expected[0]
        sentences[2] shouldBeEqualTo expected[1]
        sentences[3] shouldBeEqualTo expected[2]
    }

    @Test
    fun `should change state from loading to most recent sentence with delay`() = runTest {
        val expected = "Greetings, from AM23"

        val initialSentence = sentencesViewModel.sentencesState.value
        initialSentence shouldBeEqualTo "Loading"

        sentencesViewModel.fetchSentences()
        runCurrent()

        val mostRecentSentence = sentencesViewModel.sentencesState.value
        mostRecentSentence shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order with delay`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")
        val sentences = mutableListOf<String>()

        sentencesViewModel.sentencesDelayState
            .onEach { sentences.add(it) }
            .flowOn(UnconfinedTestDispatcher(testScheduler))
            .launchIn(backgroundScope)

        sentencesViewModel.fetchSentencesWithDelay()
        advanceUntilIdle()

        sentences[0] shouldBeEqualTo "Loading"
        sentences[1] shouldBeEqualTo expected[0]
        sentences[2] shouldBeEqualTo expected[1]
        sentences[3] shouldBeEqualTo expected[2]
    }
}