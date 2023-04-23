package com.am23.testingtheflow.viewmodels

import com.am23.testingtheflow.usecases.FetchSentencesUseCase
import com.am23.testingtheflow.usecases.FetchSentencesWithDelayUseCase
import com.am23.testingtheflow.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
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
}