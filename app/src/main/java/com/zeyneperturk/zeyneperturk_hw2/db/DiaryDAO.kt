package com.zeyneperturk.zeyneperturk_hw2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.util.Constants

@Dao
interface DiaryDAO {
    @Insert
    fun insertDiary(diary: Diary): Long

    @Update
    fun updateDiary(diary: Diary): Int

    @Delete
    fun deleteDiary(diary: Diary): Int

    @Query("DELETE FROM ${Constants.DIARYTABLE}")
    fun deleteAllDiaries()

    @Query("SELECT * FROM ${Constants.DIARYTABLE} ORDER BY id DESC")
    fun getAllDiaries(): MutableList<Diary>

}