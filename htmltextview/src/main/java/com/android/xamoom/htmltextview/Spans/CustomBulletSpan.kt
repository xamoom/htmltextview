package com.android.xamoom.htmltextview.Spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.BulletSpan
import android.util.LayoutDirection
import android.view.View

class CustomBulletSpan(gapWidth: kotlin.Int, var leading: Int?,
                       val layoutDirection: Int) : BulletSpan(gapWidth) {
  override fun getLeadingMargin(first: Boolean): Int {
    return super.getLeadingMargin(first)
  }

  override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int, top: Int, baseline: Int,
                                 bottom: Int, text: CharSequence, start: Int, end: Int,
                                 first: Boolean, l: Layout) {

    if (layoutDirection == View.LAYOUT_DIRECTION_RTL && leading != null) {
      leading = l.width - leading!!
    }

    super.drawLeadingMargin(c, p, leading ?: x, dir, top, baseline, bottom, text, start, end, first, l)
  }
}