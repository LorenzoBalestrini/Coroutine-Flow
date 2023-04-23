package com.example.myapplicationwithauthorization

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationwithauthorization.network.retrofitcall.RetrofitInstance
import com.example.myapplicationwithauthorization.repository.TriviaRepository

class MyApplication: Application() {

    lateinit var db : AppDatabase
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var preferences : SharedPreferences
    private val triviaProvider = RetrofitInstance()
    lateinit var triviaRepository: TriviaRepository

    override fun onCreate() {
        super.onCreate()

        preferences = getSharedPreferences("app", Context.MODE_PRIVATE)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        triviaRepository = TriviaRepository(triviaProvider, db.questionDao())

        mainViewModelFactory = MainViewModelFactory(triviaRepository, preferences)
    }
}