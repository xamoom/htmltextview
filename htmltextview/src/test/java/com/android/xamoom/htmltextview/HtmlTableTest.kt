package com.android.xamoom.htmltextview

import android.text.TextPaint
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class HtmlTableTest {

  @Test
  fun initTest() {
    val htmlString = "<table>" +
        "<tr>" +
        "<td>Some</td>" +
        "<td>More Data</td>" +
        "</tr>" +
        "</table>"

    val htmlTable = HtmlTable(htmlString, TextPaint())

    Assert.assertEquals(1, htmlTable.rows.size)
    Assert.assertEquals(2, htmlTable.cellsPerRow[0].size)
    Assert.assertEquals(2, htmlTable.cellSizes.size)
    Assert.assertEquals(2, htmlTable.largestCellSize.size)
  }
}