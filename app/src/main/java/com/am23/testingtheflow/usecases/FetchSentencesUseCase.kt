package com.am23.testingtheflow.usecases

import com.am23.testingtheflow.services.SentencesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FetchSentencesUseCase(
    private val sentencesService: SentencesService = SentencesService(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke(): Flow<String> =
        sentencesService.fetchSentences()
            .flowOn(context = dispatcher)
}