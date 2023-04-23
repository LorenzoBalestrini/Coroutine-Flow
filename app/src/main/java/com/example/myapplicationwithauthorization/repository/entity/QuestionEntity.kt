package com.example.myapplicationwithauthorization.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplicationwithauthorization.network.usecase.model.TriviaQuestion

@Entity(tableName = "question_table")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answer") val answer: String)

fun QuestionEntity.toModel(): TriviaQuestion {
    return TriviaQuestion( this.category, this.question, this.answer)
}

fun TriviaQuestion.toEntity(): QuestionEntity{

    return QuestionEntity(category = this.category, question = this.question, answer = this.answer)
}