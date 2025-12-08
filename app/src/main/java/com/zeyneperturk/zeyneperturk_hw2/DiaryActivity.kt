package com.zeyneperturk.zeyneperturk_hw2

import android.app.Dialog
import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeyneperturk.zeyneperturk_hw2.customViews.CustomButton
import com.zeyneperturk.zeyneperturk_hw2.customViews.DiaryView
import com.zeyneperturk.zeyneperturk_hw2.databinding.ActivityDiaryBinding
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood
import java.util.Date

class DiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createDialog()
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val existingDiary = intent.getParcelableExtra<Diary>("entry")
        val existingMoodId = intent.getIntExtra("moodId", -1)
        val existingMoodType = intent.getStringExtra("moodType")

        if (existingDiary != null) {
            binding.entry.setTitle(existingDiary.title)
            binding.entry.setContent(existingDiary.content)
            binding.entry.setMoodId(existingMoodId)

            // FIX 1: Apply mood color immediately
            binding.entry.updateColors(existingMoodId)

            // FIX 2: Simulate mood button select so selectedMood updates correctly
            binding.entry.selectMood(existingMoodId)
        }

        binding.entry.setOnMoodSelectedListener(object: DiaryView.OnMoodSelectedListener{
            override fun onMoodSelected(moodId: Int) {
                binding.entry.updateColors(moodId)
            }
        })

        binding.btnDelete.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                dialog.show()
            }
        })

        binding.btnSave.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                if(binding.entry.getTitle().isEmpty()){
                    Toast.makeText(this@DiaryActivity, R.string.toastName, Toast.LENGTH_LONG).show()
                }
                else if(binding.entry.getContent().isEmpty()){
                    Toast.makeText(this@DiaryActivity, R.string.toastContent, Toast.LENGTH_LONG).show()
                }
                else{
                    val selectedMoodId = binding.entry.getMoodId()
                    val selectedMoodType = binding.entry.getMood()

                    val finalMoodId =
                        if (existingDiary != null && selectedMoodId == 5) existingMoodId else selectedMoodId

                    val finalMoodType =
                        if (existingDiary != null && selectedMoodId == 5) existingMoodType else selectedMoodType

                    if (existingDiary == null && binding.entry.getMoodId() == 5) {
                        Toast.makeText(this@DiaryActivity, R.string.toastMood, Toast.LENGTH_LONG).show()
                        return
                    }
                    val res = Intent()
                    if (existingDiary != null) {
                        val updatedDiary = existingDiary.copy(
                            title = binding.entry.getTitle(),
                            content = binding.entry.getContent(),
                            date = Date().time
                        )

                        res.putExtra("entry", updatedDiary)
                    } else {
                        val newDiary = Diary(
                            title = binding.entry.getTitle(),
                            content = binding.entry.getContent(),
                            date = Date().time
                        )

                        res.putExtra("entry", newDiary)
                    }

                    res.putExtra("moodId", finalMoodId)
                    res.putExtra("moodType", finalMoodType)

                    setResult(RESULT_OK, res)
                    finish()
                }
            }
        })
    }

    fun createDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)

        val btnCancel = dialog.findViewById<CustomButton>(R.id.btnCancel)
        val btnProceed = dialog.findViewById<CustomButton>(R.id.btnProceed)
        val tv = dialog.findViewById<TextView>(R.id.tvDialog)

        tv.setText(R.string.dialogDeleteEntry)

        btnCancel.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                dialog.dismiss()
            }
        })

        btnProceed.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                dialog.dismiss()

                val existingDiary = intent.getParcelableExtra<Diary>("entry")
                val res = Intent()

                if (existingDiary != null) {
                    res.putExtra("entry", existingDiary)
                }

                setResult(RESULT_CANCELED, res)
                finish()
                Toast.makeText(this@DiaryActivity, R.string.quote, Toast.LENGTH_LONG).show()
            }
        })
    }
}