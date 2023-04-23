package com.example.myapplicationwithauthorization.network.usecase

import android.content.SharedPreferences
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationwithauthorization.network.usecase.model.TriviaQuestion
import com.example.myapplicationwithauthorization.repository.TriviaRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

const val KEY_FIRST_RETROFIT_CALL = "first_retrofit_call"

class MainViewModel(
    private val triviaRepository: TriviaRepository,
    private val preferences: SharedPreferences,
    ) : ViewModel() {

    sealed class TriviaViewModelState {
        data class TriviaResult(val question: List<TriviaQuestion>) : TriviaViewModelState()
        data class TriviaError(val message: String) : TriviaViewModelState()
        object LastQuestion : TriviaViewModelState()
    }

    val result = MutableSharedFlow<TriviaViewModelState>()


    init {
        getPreferences(preferences)
        setupTriviaObserver()
    }

    private fun getPreferences(preferences: SharedPreferences){
        val triviaPreferences = preferences.getBoolean(KEY_FIRST_RETROFIT_CALL, true)

        if(triviaPreferences){
            preferences.edit().putBoolean(KEY_FIRST_RETROFIT_CALL, false).apply()

            viewModelScope.launch {
                result.emit(TriviaViewModelState.LastQuestion)
            }
        }
    }

    private fun setupTriviaObserver(){
        viewModelScope.launch {
            triviaRepository.loadTrivia().collect{
                Log.d("MainViewModel", "retrieved from database")
                result.emit(TriviaViewModelState.TriviaResult(it))
            }
        }
    }

    fun retrieveDetails() {
        viewModelScope.launch {
                triviaRepository.retrieveDetails()
        }
    }
}