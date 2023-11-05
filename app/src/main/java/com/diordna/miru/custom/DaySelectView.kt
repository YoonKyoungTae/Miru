package com.diordna.miru.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.diordna.miru.R
import org.joda.time.DateTime

class DaySelectView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        inflate(context, R.layout.custom_day_select_view, this)
    }

    fun isSelected(isSelect: Boolean) {
        val backgroundView = findViewById<ImageView>(R.id.dayBackgroundView)
        val textView = findViewById<TextView>(R.id.dayTextView)

        if (isSelect) {
            backgroundView.setBackgroundColor(context.getColor(R.color.black))
            textView.setTextColor(context.getColor(R.color.white))
        } else {
            backgroundView.setBackgroundColor(context.getColor(R.color.white))
            textView.setTextColor(context.getColor(R.color.black))
        }
    }

    fun setText(text: String) {
        val textView = findViewById<TextView>(R.id.dayTextView)
        textView.text = text
    }

    fun setDate(dateTime: DateTime) {
        val today = DateTime.now().toString("yyyyMMdd")
        val diffDay = dateTime.toString("yyyyMMdd")
        if (today == diffDay) {
            isSelected(true)
        }
    }

}