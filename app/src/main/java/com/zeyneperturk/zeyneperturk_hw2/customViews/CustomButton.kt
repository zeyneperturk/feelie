package com.zeyneperturk.zeyneperturk_hw2.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.zeyneperturk.zeyneperturk_hw2.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    private var customButtonListener: CustomButtonEventListener? = null

    init {
        background = ContextCompat.getDrawable(context, R.drawable.btn_bg)
        setTextColor(ContextCompat.getColor(context, R.color.btnTXT))
        typeface = ResourcesCompat.getFont(context, R.font.dudu_calligraphy)

        setOnClickListener {
            customButtonListener?.onCustomEvent(this)
        }
    }
    interface CustomButtonEventListener {
        fun onCustomEvent(view: View)
    }
    fun setOnCustomEventListener(listener: CustomButtonEventListener) {
        customButtonListener = listener
    }
}