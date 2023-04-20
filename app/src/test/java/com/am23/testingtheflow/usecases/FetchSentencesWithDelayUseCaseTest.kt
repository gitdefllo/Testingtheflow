package com.am23.testingtheflow.usecases

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test

class FetchSentencesWithDelayUseCaseTest {

    private lateinit var fetchSentencesWithDelayUseCase: FetchSentencesWithDelayUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        fetchSentencesWithDelayUseCase = FetchSentencesWithDelayUseCase(
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `fetch first sentence`() = testScope.runTest {
        val expected = "Hello!"

        val sentences = fetchSentencesWithDelayUseCase().first()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch last sentence`() = testScope.runTest {
        val expected = "Greetings, from AM23"

        val sentences = fetchSentencesWithDelayUseCase().last()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order`() = testScope.runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val sentences = fetchSentencesWithDelayUseCase().toList()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch sentence without exception`() = testScope.runTest {
        fetchSentencesWithDelayUseCase()
            .onCompletion {
                it.shouldBeNull()
            }
            .collect()
    }
}