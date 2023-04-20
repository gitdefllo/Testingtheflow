package com.am23.testingtheflow.usecases

import com.am23.testingtheflow.services.SentencesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FetchSentencesWithDelayUseCase(
    private val sentencesService: SentencesService = SentencesService()
) {

    operator fun invoke(): Flow<String> =
        sentencesService.fetchSentencesWithDelay()
            .flowOn(context = Dispatchers.IO)
}