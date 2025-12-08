package com.zeyneperturk.zeyneperturk_hw2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood
import com.zeyneperturk.zeyneperturk_hw2.util.Constants

@Dao
interface MoodDAO {
    @Insert
    fun insertMood(mood: Mood): Long

    @Update
    fun updateMood(mood: Mood): Int

    @Delete
    fun deleteMood(mood: Mood): Int

    @Query("DELETE FROM ${Constants.MOODTABLE}")
    fun deleteAllMoods()

    @Query("SELECT * FROM ${Constants.MOODTABLE} ORDER BY diaryId DESC")
    fun getAllMoods(): MutableList<Mood>

    @Query("DELETE FROM ${Constants.MOODTABLE} WHERE diaryId = :diaryId")
    fun deleteMoodByDiaryId(diaryId: Int)
}