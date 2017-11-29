package com.android.xamoom.htmltextview.Spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.TextPaint
import android.text.style.LeadingMarginSpan

class NumberSpan(leadingMargin: Int, var numberText: String,
                 textPaint: TextPaint) : LeadingMarginSpan.Standard(leadingMargin) {
  var textWidth: Int = 0
  var leadingMargin: Int = 0
  var innerTextMargin: Int = 10

  init {
    textWidth = textPaint.measureText(numberText).toInt()
    numberText = String.format("%s.", numberText)
    this.leadingMargin = leadingMargin
  }

  override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int,
                                 bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean,
                                 l: Layout?) {
    // by checking if it's leadingMargin, I don't draw the number in front
    // this prevents that ol listItems that are longer than the screenwidth
    // don't have the number in front of every line
    var leadingText = numberText
    if (!first) {
      leadingText = ""
    }

    c?.drawText(leadingText, (x + leadingMargin).toFloat(), baseline.toFloat(), p)
  }

  override fun getLeadingMargin(p0: Boolean): Int {
    return textWidth + leadingMargin + innerTextMargin
  }
}