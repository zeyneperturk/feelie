package com.zeyneperturk.zeyneperturk_hw2

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeyneperturk.zeyneperturk_hw2.adapter.CustomRecyclerViewAdapter
import com.zeyneperturk.zeyneperturk_hw2.customViews.CustomButton
import com.zeyneperturk.zeyneperturk_hw2.databinding.ActivityMainBinding
import com.zeyneperturk.zeyneperturk_hw2.databinding.DialogBinding
import com.zeyneperturk.zeyneperturk_hw2.db.DiaryRoomDatabase
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood
import com.zeyneperturk.zeyneperturk_hw2.util.Constants
import java.util.Collections

class MainActivity : AppCompatActivity() , CustomRecyclerViewAdapter.RecyclerAdapterInterface{

    lateinit var binding: ActivityMainBinding
    lateinit var dialog: Dialog
    lateinit var layoutManager: LinearLayoutManager

    private val diaryDB: DiaryRoomDatabase by lazy {
        Room.databaseBuilder(this, DiaryRoomDatabase::class.java,  Constants.DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.entries.layoutManager=layoutManager

        val diaryList = diaryDB.diaryDAO().getAllDiaries()
        val moodList = diaryDB.moodDAO().getAllMoods()

        val adapter = CustomRecyclerViewAdapter(this, diaryList as ArrayList<Diary>,
            moodList as ArrayList<Mood>
        )
        binding.entries.setAdapter(adapter)

        binding.btnNew.setOnCustomEventListener(object : CustomButton.CustomButtonEventListener {
            override fun onCustomEvent(view: View) {
                val intent = Intent(this@MainActivity, DiaryActivity::class.java)
                resultLauncher.launch(intent)
            }
        })

        binding.btnDeleteAll.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                dialog.show()
            }
        })

        createDialog()
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == RESULT_OK){
            val res = result.data
            val newDiary = res?.getParcelableExtra<Diary>("entry")!!
            val newMoodID = res.getIntExtra("moodId", 5)
            val newMoodType = res.getStringExtra("moodType") ?: ""

            val newDiaryID = diaryDB.diaryDAO().insertDiary(newDiary).toInt()
            diaryDB.moodDAO().insertMood(Mood(newDiaryID, newMoodID, newMoodType))
            updateData()
        }
    }

    private val resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == RESULT_OK){
            val res = result.data
            val updatedDiary = res?.getParcelableExtra<Diary>("entry")!!
            val updatedMoodId = res.getIntExtra("moodId", -1)
            val updatedMoodType = res.getStringExtra("moodType") ?: ""

            diaryDB.diaryDAO().updateDiary(updatedDiary)

            diaryDB.moodDAO().updateMood(
                Mood(
                    diaryId = updatedDiary.id,
                    moodId = updatedMoodId,
                    moodType = updatedMoodType
                )
            )

            updateData()
        }else if(result.resultCode == RESULT_CANCELED){
            val data = result.data!!
            val diaryToDelete = data.getParcelableExtra<Diary>("entry")!!

            diaryDB.diaryDAO().deleteDiary(diaryToDelete)
            diaryDB.moodDAO().deleteMoodByDiaryId(diaryToDelete.id)

            updateData()
        }
    }

    fun createDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)

        val btnCancel = dialog.findViewById<CustomButton>(R.id.btnCancel)
        val btnProceed = dialog.findViewById<CustomButton>(R.id.btnProceed)
        val tv = dialog.findViewById<TextView>(R.id.tvDialog)

        tv.setText(R.string.dialogDeleteAll)

        btnCancel.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                dialog.dismiss()
            }
        })

        btnProceed.setOnCustomEventListener(object: CustomButton.CustomButtonEventListener{
            override fun onCustomEvent(view: View) {
                diaryDB.diaryDAO().deleteAllDiaries()
                dialog.dismiss()
                updateData()
                Toast.makeText(this@MainActivity, R.string.quote, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun displayItem(diary: Diary, mood: Mood) {
        val intent = Intent(this, DiaryActivity::class.java)
        intent.putExtra("entry", diary)
        intent.putExtra("moodId", mood.moodId)
        intent.putExtra("moodType", mood.moodType)
        resultLauncher2.launch(intent)
    }

    override fun updateData() {
        val diaries = diaryDB.diaryDAO().getAllDiaries()
        val moods = diaryDB.moodDAO().getAllMoods()

        val adapter = CustomRecyclerViewAdapter(
            this,
            ArrayList(diaries),
            ArrayList(moods)
        )

        binding.entries.adapter = adapter
    }
}