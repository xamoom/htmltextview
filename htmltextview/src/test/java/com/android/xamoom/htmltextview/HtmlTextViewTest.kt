package com.android.xamoom.htmltextview

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class HtmlTextViewTest {
  val context = RuntimeEnvironment.application

  @Test
  fun replaceStyleTest() {
    val textView = HtmlTextView(context)

    var htmlText = "<span style=\"font-weight: bold;\"></span>"
    htmlText += "<span style=\"text-decoration: underline;\"></span>"
    htmlText += "<span style=\"font-style: italic;\"></span>"
    htmlText += "<span style=\"font-size: 18px;\"></span>"
    htmlText += "<ul><li></li></ul>"
    htmlText += "<ol><li></li></ol>"
    htmlText += "<table><tr><td></td></tr></table>"

    textView.setHtmlText(htmlText)

    assertEquals("<html>" +
        "\n <head></head>" +
        "\n <body>" +
        "\n  <b><span style=\"font-weight: bold;\"></span></b>" +
        "\n  <u><span style=\"text-decoration: underline;\"></span></u>" +
        "\n  <i><span style=\"font-style: italic;\"></span></i>" +
        "\n  <fontsize18px>" +
        "\n   <span style=\"font-size: 18px;\"></span>" +
        "\n  </fontsize18px>" +
        "\n  <unorderedlist>" +
        "\n   <listitem></listitem>" +
        "\n  </unorderedlist>" +
        "\n  <orderedlist>" +
        "\n   <listitem></listitem>" +
        "\n  </orderedlist>" +
        "\n  <htmltable>" +
        "\n   <tbody>" +
        "\n    <tablerow>" +
        "\n     <tablecell></tablecell>" +
        "\n    </tablerow>" +
        "\n   </tbody>" +
        "\n  </htmltable>" +
        "\n </body>" +
        "\n</html>", textView.text)
  }

  @Test
  fun getTablesTest() {
    val textView = HtmlTextView(context)

    var htmlText = "<table><tr><td></td></tr></table>"
    htmlText += "<table><tr><td></td></tr></table>"

    textView.setHtmlText(htmlText)

    assertEquals(2, textView.tables.size)
  }

}