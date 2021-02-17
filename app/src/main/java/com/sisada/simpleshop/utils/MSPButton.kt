package com.sisada.simpleshop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.sisada.simpleshop.R

class MSPButton(context: Context, attrs: AttributeSet) : AppCompatButton(context,attrs) {

    init{
        applyFont()
    }

    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)

        setTextColor(resources.getColor(R.color.colorPrimaryText))
    }
}