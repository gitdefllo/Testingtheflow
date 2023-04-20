package com.am23.testingtheflow.services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class SentencesService {

    fun fetchSentences(): Flow<String> = flow {
        emit("Hello!")
        emit("Nice to see you.")
        emit("Greetings, from AM23")
    }

    fun fetchSentencesWithDelay(): Flow<String> = flow {
        delay(5.seconds)
        emit("Hello!")
        delay(5.seconds)
        emit("Nice to see you.")
        delay(5.seconds)
        emit("Greetings, from AM23")
    }
}