package com.example.myapplicationwithauthorization.repository

import android.util.Log
import com.example.myapplicationwithauthorization.network.retrofitcall.RetrofitInstance
import com.example.myapplicationwithauthorization.network.usecase.model.TriviaQuestion
import com.example.myapplicationwithauthorization.repository.dao.QuestionDao
import com.example.myapplicationwithauthorization.repository.entity.toEntity
import com.example.myapplicationwithauthorization.repository.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class TriviaRepository(
    private val triviaProvider: RetrofitInstance,
    private  val questionDao: QuestionDao
) {
    @Suppress("RedundantSuspendModifier")
    suspend fun loadTrivia(): Flow<List<TriviaQuestion>> {
        return questionDao.getAll().map { list -> list.map { it.toModel() } }
    }

    suspend fun retrieveDetails() {
        try{
            val dataFromNetwork = triviaProvider.getDataQuestion()
            questionDao.insertAll(*dataFromNetwork.map { question -> question.toEntity()}.toTypedArray())
            }catch (e:Exception){
                Log.w("TriviaRepository", "error loading data from network: $e")
            }
    }

}