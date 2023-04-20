package com.am23.testingtheflow.usecases

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test

class FetchSentencesUseCaseTest {

    private lateinit var fetchSentencesUseCase: FetchSentencesUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        fetchSentencesUseCase = FetchSentencesUseCase(
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `fetch first sentence`() = testScope.runTest {
        val expected = "Hello!"

        val sentences = fetchSentencesUseCase().first()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch last sentence`() = testScope.runTest {
        val expected = "Greetings, from AM23"

        val sentences = fetchSentencesUseCase().last()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order`() = testScope.runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val sentences = fetchSentencesUseCase().toList()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch sentence without exception`() = testScope.runTest {
        fetchSentencesUseCase()
            .onCompletion {
                it.shouldBeNull()
            }
            .collect()
    }
}