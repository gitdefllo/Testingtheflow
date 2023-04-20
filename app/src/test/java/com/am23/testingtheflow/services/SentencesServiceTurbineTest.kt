package com.am23.testingtheflow.services

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class SentencesServiceTurbineTest {

    private val sentencesService = SentencesService()

    @Test
    fun `fetch first sentence`() = runTest {
        val expected = "Hello!"

        sentencesService.fetchSentences().test {
            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch last sentence`() = runTest {
        val expected = "Greetings, from AM23"

        sentencesService.fetchSentences().test {
            skipItems(2)

            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            awaitComplete()
        }
    }

    @Test
    fun `fetch all sentences in order`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        sentencesService.fetchSentences().test {
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
    fun `fetch sentence without exception`() = runTest {
        sentencesService.fetchSentences().test {
            skipItems(3)
            awaitComplete()
        }
    }

    // ----------------------
    // Unit Tests with delays
    // ----------------------

    @Test
    fun `fetch first sentence with delay`() = runTest {
        val expected = "Hello!"

        sentencesService.fetchSentencesWithDelay().test {
            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch last sentence with delay`() = runTest {
        val expected = "Greetings, from AM23"

        sentencesService.fetchSentencesWithDelay().test {
            skipItems(2)

            val sentence = awaitItem()
            sentence shouldBeEqualTo expected

            awaitComplete()
        }
    }

    @Test
    fun `fetch all sentences in order with delay`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        sentencesService.fetchSentencesWithDelay().test {
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
    fun `fetch sentence with delay without exception`() = runTest {
        sentencesService.fetchSentencesWithDelay().test {
            skipItems(3)
            awaitComplete()
        }
    }
}