package com.example.myapplicationwithauthorization.network.usecase

import android.content.SharedPreferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationwithauthorization.network.retrofitcall.RetrofitInstance
import com.example.myapplicationwithauthorization.network.usecase.model.TriviaQuestion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

const val KEY_FIRST_RETROFIT_CALL = "first_retrofit_call"

class MainViewModel(private val triviaProvider: RetrofitInstance, private val preferences: SharedPreferences) : ViewModel() {

    sealed class TriviaViewModelEvent {
        data class TriviaResult(val question: List<TriviaQuestion>) : TriviaViewModelEvent()
        data class TriviaError(val message: String) : TriviaViewModelEvent()
        object LastQuestion : TriviaViewModelEvent()
    }

    val result = MutableSharedFlow<TriviaViewModelEvent>()


    init {
        getPreferences(preferences)
    }

    private fun getPreferences(preferences: SharedPreferences){
        val triviaPreferences = preferences.getBoolean(KEY_FIRST_RETROFIT_CALL, true)

        if(triviaPreferences){
            preferences.edit().putBoolean(KEY_FIRST_RETROFIT_CALL, false).apply()

            viewModelScope.launch {
                result.emit(TriviaViewModelEvent.LastQuestion)
            }
        }
    }

    fun retrieveDetails() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                result.emit(
                    TriviaViewModelEvent.TriviaResult(triviaProvider.getDataQuestion())
                )
            } catch (e: Exception) {
                result.emit(
                    TriviaViewModelEvent.TriviaError("Error: ${e.localizedMessage}")
                )
            }
        }
    }
}