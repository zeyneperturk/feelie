package com.zeyneperturk.zeyneperturk_hw2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeyneperturk.zeyneperturk_hw2.util.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.MOODTABLE)
class Mood(
    @PrimaryKey
    var diaryId: Int,
    var moodId: Int,
    var moodType: String
) : Parcelable{
    companion object{
        const val SAD = 0
        const val HAPPY = 1
        const val CALM = 2
        const val ANGRY = 3
    }
}