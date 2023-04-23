package com.am23.testingtheflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am23.testingtheflow.usecases.FetchSentencesUseCase
import com.am23.testingtheflow.usecases.FetchSentencesWithDelayUseCase
import kotlinx.coroutines.flow.*

class SentencesViewModel(
    private val fetchSentencesUseCase: FetchSentencesUseCase,
    private val fetchSentencesWithDelayUseCase: FetchSentencesWithDelayUseCase
) : ViewModel() {

    private val _sentencesState: MutableStateFlow<String> = MutableStateFlow("Loading")
    val sentencesState: StateFlow<String> = _sentencesState.asStateFlow()
    private val _sentencesDelayState: MutableStateFlow<String> = MutableStateFlow("Loading")
    val sentencesDelayState: StateFlow<String> = _sentencesDelayState.asStateFlow()

    fun fetchSentences() {
        fetchSentencesUseCase()
            .onEach { _sentencesState.emit(it) }
            .catch { _sentencesState.emit(it.toString()) }
            .launchIn(viewModelScope)
    }

    fun fetchSentencesWithDelay() {
        fetchSentencesWithDelayUseCase()
            .onEach { _sentencesDelayState.emit(it) }
            .catch { _sentencesDelayState.emit(it.toString()) }
            .launchIn(viewModelScope)
    }
}