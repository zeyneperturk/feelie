package com.zeyneperturk.zeyneperturk_hw2.customViews

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zeyneperturk.zeyneperturk_hw2.R
import com.zeyneperturk.zeyneperturk_hw2.databinding.DiaryFormBinding
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood

class DiaryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var moodListener: OnMoodSelectedListener ?= null
    private val binding = DiaryFormBinding.inflate(LayoutInflater.from(context), this, true)
    var selectedMood = 5

    init {
        binding.moodSad.setOnClickListener { selectMood(0) }
        binding.moodHappy.setOnClickListener { selectMood(1) }
        binding.moodCalm.setOnClickListener { selectMood(2) }
        binding.moodAngry.setOnClickListener { selectMood(3) }
    }

    fun getTitle(): String {
        return binding.etTitle.text.toString()
    }

    fun getContent(): String {
        return binding.etContent.text.toString()
    }

    fun getMoodId(): Int{
        return selectedMood
    }

    fun setTitle(title: String){
        binding.etTitle.setText(title)
    }

    fun setContent(content: String) {
        binding.etContent.setText(content)
    }

    fun setMoodId(moodId: Int){
        selectedMood = moodId
    }

    fun getMood(): String {
        val mood = when(selectedMood){
            Mood.SAD -> "sad"
            Mood.HAPPY -> "happy"
            Mood.CALM -> "calm"
            Mood.ANGRY -> "angry"
            else -> "calm"
        }
        return mood
    }

    fun selectMood(moodId: Int) {
        selectedMood = moodId
        moodListener?.onMoodSelected(moodId)
    }


    interface OnMoodSelectedListener{
        fun onMoodSelected(moodId: Int)
    }

    fun setOnMoodSelectedListener(listener: OnMoodSelectedListener){
        moodListener = listener
    }

    fun updateColors(moodId: Int){
        val titleBG = binding.etTitle.background.mutate() as GradientDrawable
        val  contentBG = binding.etContent.background.mutate() as GradientDrawable

        var bgColor = 0
        var borderColor : Int = 0

        when(moodId){
            Mood.SAD -> {
                bgColor = R.color.sadBG
                borderColor = R.color.sadTXT

            }
            Mood.HAPPY -> {
                bgColor = R.color.happyBG
                borderColor = R.color.happyTXT
            }
            Mood.CALM -> {
                bgColor = R.color.calmBG
                borderColor = R.color.calmTXT
            }
            Mood.ANGRY -> {
                bgColor = R.color.angryBG
                borderColor = R.color.angryTXT
            }
        }

        titleBG.setColor(ContextCompat.getColor(context, bgColor))
        titleBG.setStroke(15, ContextCompat.getColor(context, borderColor))

        contentBG.setColor(ContextCompat.getColor(context, bgColor))
        contentBG.setStroke(15, ContextCompat.getColor(context, borderColor))
    }
}
