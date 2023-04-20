package com.am23.testingtheflow.services

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test

class SentencesServiceTest {

    private val sentencesService = SentencesService()

    @Test
    fun `fetch first sentence`() = runTest {
        val expected = "Hello!"

        val sentences = sentencesService.fetchSentences().first()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch last sentence`() = runTest {
        val expected = "Greetings, from AM23"

        val sentences = sentencesService.fetchSentences().last()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val sentences = sentencesService.fetchSentences().toList()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch sentence without exception`() = runTest {
        sentencesService.fetchSentences()
            .onCompletion {
                it.shouldBeNull()
            }
            .collect()
    }

    // ----------------------
    // Unit Tests with delays
    // ----------------------

    @Test
    fun `fetch first sentence with delay`() = runTest {
        val expected = "Hello!"

        val sentences = sentencesService.fetchSentencesWithDelay().first()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch last sentence with delay`() = runTest {
        val expected = "Greetings, from AM23"

        val sentences = sentencesService.fetchSentencesWithDelay().last()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch all sentences in order with delay`() = runTest {
        val expected = listOf("Hello!", "Nice to see you.", "Greetings, from AM23")

        val sentences = sentencesService.fetchSentencesWithDelay().toList()

        sentences shouldBeEqualTo expected
    }

    @Test
    fun `fetch sentences with delay without exception`() = runTest {
        sentencesService.fetchSentencesWithDelay()
            .onCompletion {
                it.shouldBeNull()
            }
            .collect()
    }
}