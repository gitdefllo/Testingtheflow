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
    fun `fetch all sentences in order`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val initialSentence = sentencesViewModel.sentencesState.value
        initialSentence shouldBeEqualTo "Loading"

        sentencesViewModel.fetchSentences()
        runCurrent()

        val firstSentence = sentencesViewModel.sentencesState.value
        firstSentence shouldBeEqualTo expected[0]

        val secondSentence = sentencesViewModel.sentencesState.value
        secondSentence shouldBeEqualTo expected[1]

        val thirdSentence = sentencesViewModel.sentencesState.value
        thirdSentence shouldBeEqualTo expected[2]
    }
}