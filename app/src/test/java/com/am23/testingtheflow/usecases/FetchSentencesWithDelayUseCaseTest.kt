package com.am23.testingtheflow.usecases

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test

class FetchSentencesWithDelayUseCaseTest {

    private val fetchSentencesWithDelayUseCase = FetchSentencesWithDelayUseCase()

    @Test
    fun `fetch first sentence`() = runTest {
        val expected = "Hello!"

        val sentences = fetchSentencesWithDelayUseCase().first()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch last sentence`() = runTest {
        val expected = "Greetings, from AM23"

        val sentences = fetchSentencesWithDelayUseCase().last()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val sentences = fetchSentencesWithDelayUseCase().toList()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch sentence without exception`() = runTest {
        fetchSentencesWithDelayUseCase()
            .onCompletion {
                it.shouldBeNull()
            }
            .collect()
    }
}