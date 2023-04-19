package com.example.myapplicationwithauthorization.network.dto

import com.example.myapplicationwithauthorization.network.usecase.model.TriviaQuestion
import com.google.gson.annotations.SerializedName

data class DataItem(
    val category: String,
    val question: String,
    val answer: String
)

fun DataItem.toTriviaQuestion() = TriviaQuestion(this.category, this.question, this.answer)