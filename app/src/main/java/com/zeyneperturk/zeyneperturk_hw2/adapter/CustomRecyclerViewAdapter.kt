package com.zeyneperturk.zeyneperturk_hw2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeyneperturk.zeyneperturk_hw2.R
import com.zeyneperturk.zeyneperturk_hw2.databinding.RecyclerAngryItemBinding
import com.zeyneperturk.zeyneperturk_hw2.databinding.RecyclerCalmItemBinding
import com.zeyneperturk.zeyneperturk_hw2.databinding.RecyclerHappyItemBinding
import com.zeyneperturk.zeyneperturk_hw2.databinding.RecyclerSadItemBinding
import com.zeyneperturk.zeyneperturk_hw2.model.Diary
import com.zeyneperturk.zeyneperturk_hw2.model.Mood

class CustomRecyclerViewAdapter( private val context: Context, private val diaries: ArrayList<Diary>, private val moods: ArrayList<Mood>)
    : RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    interface RecyclerAdapterInterface{
        fun displayItem(diary: Diary, mood: Mood)
        fun updateData()
    }
    lateinit var recyclerAdapterInterface:RecyclerAdapterInterface

    init {
        recyclerAdapterInterface = context as RecyclerAdapterInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            Mood.SAD -> {
                val binding = RecyclerSadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SadCustomRecyclerViewItemHolder(binding)
            }
            Mood.HAPPY -> {
                val binding = RecyclerHappyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HappyCustomRecyclerViewItemHolder(binding)
            }
            Mood.CALM -> {
                val binding = RecyclerCalmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CalmCustomRecyclerViewItemHolder(binding)
            }
            Mood.ANGRY -> {
                val binding = RecyclerAngryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AngryCustomRecyclerViewItemHolder(binding)
            }
            else -> {
                val binding = RecyclerCalmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CalmCustomRecyclerViewItemHolder(binding)
            }
        }
    }

    /*override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflator = LayoutInflater.from(viewGroup.context)
        //STEP4
        return if (viewType == TYPE_EVEN_ITEM) {
            //STEP5
            itemView = inflator.inflate(R.layout.recycler_even_item, viewGroup, false)
            EvenCustomRecyclerViewItemHolder(itemView)
        } else { //if(viewType == TYPE_ODD_ITEM) odd item
            //STEP5
            itemView = inflator.inflate(R.layout.recycler_odd_item, viewGroup, false)
            OddCustomRecyclerViewItemHolder(itemView)
        }
    }*/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val diary = diaries[position]
        val mood = moods[position]

        when (mood.moodId) {

            Mood.SAD -> {
                val h = holder as SadCustomRecyclerViewItemHolder
                h.bindingSad.tvTitle.text = diary.title
                h.bindingSad.tvPreview.text = diary.content
                h.bindingSad.tvDate.text = formatDate(diary.date)
                h.bindingSad.imgMood.setImageResource(R.drawable.sad)

                h.itemView.setOnClickListener {
                    recyclerAdapterInterface.displayItem(diary, mood)
                }
            }

            Mood.HAPPY -> {
                val h = holder as HappyCustomRecyclerViewItemHolder
                h.bindingHappy.tvTitle.text = diary.title
                h.bindingHappy.tvPreview.text = diary.content
                h.bindingHappy.tvDate.text = formatDate(diary.date)
                h.bindingHappy.imgMood.setImageResource(R.drawable.happy)

                h.itemView.setOnClickListener {
                    recyclerAdapterInterface.displayItem(diary, mood)
                }
            }

            Mood.CALM -> {
                val h = holder as CalmCustomRecyclerViewItemHolder
                h.bindingCalm.tvTitle.text = diary.title
                h.bindingCalm.tvPreview.text = diary.content
                h.bindingCalm.tvDate.text = formatDate(diary.date)
                h.bindingCalm.imgMood.setImageResource(R.drawable.calm)

                h.itemView.setOnClickListener {
                    recyclerAdapterInterface.displayItem(diary, mood)
                }
            }

            Mood.ANGRY -> {
                val h = holder as AngryCustomRecyclerViewItemHolder
                h.bindingAngry.tvTitle.text = diary.title
                h.bindingAngry.tvPreview.text = diary.content
                h.bindingAngry.tvDate.text = formatDate(diary.date)
                h.bindingAngry.imgMood.setImageResource(R.drawable.angry)

                h.itemView.setOnClickListener {
                    recyclerAdapterInterface.displayItem(diary, mood)
                }
            }
        }
    }

    private fun formatDate(time: Long): String {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(time))
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

    override fun getItemViewType(position: Int): Int {
        return moods[position].moodId
    }

    //Using viewBinding

    inner class HappyCustomRecyclerViewItemHolder(val bindingHappy: RecyclerHappyItemBinding)
        : RecyclerView.ViewHolder(bindingHappy.root){

        }

    inner class SadCustomRecyclerViewItemHolder(val bindingSad: RecyclerSadItemBinding)
        : RecyclerView.ViewHolder(bindingSad.root){

    }

    inner class CalmCustomRecyclerViewItemHolder(val bindingCalm: RecyclerCalmItemBinding)
        : RecyclerView.ViewHolder(bindingCalm.root){

    }

    inner class AngryCustomRecyclerViewItemHolder(val bindingAngry: RecyclerAngryItemBinding)
        : RecyclerView.ViewHolder(bindingAngry.root){

    }


    //STEP2
    /*internal inner class EvenCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvEvenItemSocialName: TextView
        var imgEvenItemSocial: ImageView
        var itemEvenConstraintLayout: ConstraintLayout

        init {
            tvEvenItemSocialName = itemView.findViewById<TextView>(R.id.tvEvenItemSocialName)
            imgEvenItemSocial = itemView.findViewById<ImageView>(R.id.imgEvenItemSocial)
            itemEvenConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.evenItemLayout)
        }
    }*/

    //STEP2
    /*internal inner class OddCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvOddItemSocialName: TextView
        var cbOddItem: CheckBox
        var itemOddConstraintLayout: ConstraintLayout

        init {
            tvOddItemSocialName = itemView.findViewById<TextView>(R.id.tvOddItemSocialName)
            cbOddItem = itemView.findViewById<CheckBox>(R.id.cbOddItem)
            itemOddConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.oddItemLayout)
        }
    }*/

}
