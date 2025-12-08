package com.zeyneperturk.zeyneperturk_hw2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeyneperturk.zeyneperturk_hw2.util.Constants
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = Constants.DIARYTABLE)
data class Diary(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var content: String,
    var date: Long
) : Parcelable {

    companion object {
        fun prepareData(): ArrayList<Diary> {
            val list = ArrayList<Diary>()

            list.add(
                Diary(
                    title = "A Relaxing Day",
                    content = "Went to the park and enjoyed the weather.",
                    date = Date().time
                )
            )

            list.add(
                Diary(
                    title = "Feeling Great",
                    content = "Had a productive day at work.",
                    date = Date().time
                )
            )

            list.add(
                Diary(
                    title = "A Bit Upset",
                    content = "Some things didn’t go as planned today.",
                    date = Date().time
                )
            )

            list.add(
                Diary(
                    title = "Frustrated",
                    content = "Got stuck in traffic for hours!",
                    date = Date().time
                )
            )

            return list
        }
    }

}
