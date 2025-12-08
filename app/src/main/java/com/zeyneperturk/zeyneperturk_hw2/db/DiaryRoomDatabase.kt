package com.zeyneperturk.zeyneperturk_hw2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood

@Database(entities = [Diary::class, Mood::class], version = 3)
abstract class DiaryRoomDatabase : RoomDatabase(){
    abstract fun diaryDAO(): DiaryDAO
    abstract fun moodDAO(): MoodDAO
}