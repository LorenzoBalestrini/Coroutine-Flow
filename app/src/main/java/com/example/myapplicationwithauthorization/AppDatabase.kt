package com.example.myapplicationwithauthorization

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplicationwithauthorization.repository.dao.QuestionDao
import com.example.myapplicationwithauthorization.repository.entity.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}