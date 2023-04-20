package com.am23.testingtheflow.usecases

import app.cash.turbine.test
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class FetchSentencesUseCaseTurbineTest {

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

        fetchSentencesUseCase().test {
            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch last sentence`() = testScope.runTest {
        val expected = "Greetings, from AM23"

        fetchSentencesUseCase().test {
            skipItems(2)

            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            awaitComplete()
        }
    }

    @Test
    fun `fetch all sentences in order`() = testScope.runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        fetchSentencesUseCase().test {
            val firstSentence = awaitItem()
            firstSentence shouldBeEqualTo expected[0]

            val secondSentence = awaitItem()
            secondSentence shouldBeEqualTo expected[1]

            val thirdSentence = awaitItem()
            thirdSentence shouldBeEqualTo expected[2]

            awaitComplete()
        }
    }

    @Test
    fun `fetch sentence without exception`() = testScope.runTest {
        fetchSentencesUseCase().test {
            skipItems(3)
            awaitComplete()
        }
    }
}