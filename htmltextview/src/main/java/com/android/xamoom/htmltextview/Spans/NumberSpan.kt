package com.android.xamoom.htmltextview.Spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.TextPaint
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.view.View

class NumberSpan(leadingMargin: Int, var numberText: String,
                 textPaint: TextPaint, val layoutDirection: Int) : LeadingMarginSpan.Standard(leadingMargin) {
  var textWidth: Int = 0
  var leadingMargin: Int = 0
  var innerTextMargin: Int = 20

  init {
    textWidth = textPaint.measureText(numberText).toInt()
    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      numberText = String.format(".%s", numberText)
    } else {
      numberText = String.format("%s.", numberText)
    }

    this.leadingMargin = leadingMargin
  }

  override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int,
                                 bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean,
                                 l: Layout?) {
    // by checking if it's leadingMargin, I don't draw the number in front
    // this prevents that ol listItems that are longer than the screenwidth
    // don't have the number in front of every line
    var leadingText = ""
    if (first) {
      leadingText = numberText
    }

    var myX = 0.0f
    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      // draws numbers from the right side. Needs to substract size of leadingText, cause
      // text will start drawling from the left.
      val textSize = p?.measureText(leadingText)
      if (textSize != null) {
        myX = (x - leadingMargin - textSize)
      }
    } else {
      myX = (x + leadingMargin).toFloat()
    }

    c?.drawText(leadingText, myX, baseline.toFloat(), p)
  }

  override fun getLeadingMargin(p0: Boolean): Int {
    return textWidth + leadingMargin + innerTextMargin
  }
}