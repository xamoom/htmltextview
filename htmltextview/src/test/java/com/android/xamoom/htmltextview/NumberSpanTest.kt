package com.android.xamoom.htmltextview

import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.view.View
import com.android.xamoom.htmltextview.Spans.NumberSpan
import org.junit.Test
import org.mockito.ArgumentMatchers.anyFloat
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito


class NumberSpanTest {

  @Test
  fun testDrawLeadingMargin() {
    val mockCanvas = Mockito.mock(Canvas::class.java)
    val mockPaint = Mockito.mock(TextPaint::class.java)
    Mockito.`when`(mockPaint.measureText(anyString())).thenReturn(10.0f)

    val numberSpan = NumberSpan(30, "1", mockPaint, View.LAYOUT_DIRECTION_LTR)

    // test first true
    numberSpan.drawLeadingMargin(mockCanvas, mockPaint, 0,0,0,0,0,
        "",0,0,true,null)

    Mockito.verify(mockCanvas).drawText(Mockito.eq("1."), anyFloat(), anyFloat(),
        Mockito.any(TextPaint::class.java))

    // test first false
    numberSpan.drawLeadingMargin(mockCanvas, mockPaint, 0,0,0,0,0,
        "",0,0,false,null)

    Mockito.verify(mockCanvas).drawText(Mockito.eq(""), anyFloat(), anyFloat(),
        Mockito.any(TextPaint::class.java))
  }
}