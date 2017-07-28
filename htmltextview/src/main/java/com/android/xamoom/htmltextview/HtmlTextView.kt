package com.android.xamoom.htmltextview


import android.content.Context
import android.text.Html
import android.widget.TextView
import org.jsoup.Jsoup

class HtmlTextView(context: Context) : TextView(context) {
  var htmlString: String? = null

  public fun setHtmlText(htmlString: String) {
    this.htmlString = htmlString
    text = replaceStyle(htmlString)
  }

  private fun replaceStyle(text: String): String {
    val document = Jsoup.parse(text)
    val elements = document.select("span")
    elements.addAll(document.select("span > span"))
    elements.addAll(document.select("span > span > span"))
    elements.addAll(document.select("span > span > span > span"))

    for (element in elements) {
      if (element.attributes().hasKey("style")) {

        // bold
        if (element.attributes().get("style").contains("font-weight: bold;", true)) {
          element.wrap("<b></b>")
        }

        // underline
        if (element.attributes().get("style").contains("text-decoration: underline;", true)) {
          element.wrap("<u></u>")
        }

        // italic
        if (element.attributes().get("style").contains("font-style: italic;", true)) {
          element.wrap("<i></i>")
        }

        // fontsize
        if (element.attributes().get("style").contains("font-size", true)) {
          val p1 = element.attributes().get("style")
          val indexFontSizeValue = p1!!.indexOf("font-size:") + 10
          val indexFontSizeSemiColon = p1!!.indexOf(";")
          var fontSizeString = p1!!.substring(indexFontSizeValue, indexFontSizeSemiColon);
          fontSizeString = fontSizeString.replace(" ", "")

          val name = "<FONTSIZE"+fontSizeString+"></FONTSIZE"+fontSizeString+">";
          element.wrap(name)
        }
      }
    }

    val uls = document.select("ul")
    for (element in uls) {
      element.tagName("unorderedlist")
    }

    val ols = document.select("ol")
    for (element in ols) {
      element.tagName("orderedlist")
    }

    val lis = document.select("li")
    for (element in lis) {
      element.tagName("listitem")
    }

    val tables = document.select("table")
    for (element in tables) {
      element.tagName("htmltable")
    }

    val rows = document.select("tr")
    for (element in rows) {
      element.tagName("tablerow")
    }

    val cells = document.select("td")
    for (element in cells) {
      element.tagName("tablecell")
    }

    return document.html()
  }
}
