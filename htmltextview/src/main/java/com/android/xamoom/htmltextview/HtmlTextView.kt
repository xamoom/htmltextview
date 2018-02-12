package com.android.xamoom.htmltextview


import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap


class HtmlTextView constructor(context: Context, attributeSet: AttributeSet?) :
    TextView(context, attributeSet) {
  val TAG = "HtmlTextView"

  companion object {
    var DEBUG = false
      set(value) {
        field = value
        HtmlTagHandler.DEBUG = value
      }
  }

  var htmlString: String? = null
  var tables: ArrayList<HtmlTable> = ArrayList()
  var htmlTagHandler: HtmlTagHandler? = null

  var removeTrailingNewLines = false
  var currentLayoutDirection = resources.configuration.layoutDirection

  constructor(context: Context) : this(context, null)

  init {
    movementMethod = LinkMovementMethod.getInstance()
  }

  /**
   * Set html as String to display in the textView.
   *
   * If the htmlString contains one or multiple tables, the textview
   * will wait for getting a measuring of it's width before it will
   * start displaying the string.
   *
   * You can jump over this process, by providing a maximal width.
   *
   * @see setHtmlText(String, Int)
   *
   * @param htmlString String of html
   */
  fun setHtmlText(htmlString: String) {
    this.htmlString = prepareHtmlString(htmlString)
    tables = tablesFromHtml(htmlString, paint)

    if (tables.size > 0) {
      afterMeasured {
        htmlTagHandler = HtmlTagHandler(densityTextSize(context), paint, tables,
            widthWithoutPadding(), currentLayoutDirection)
        var htmlText = Html.fromHtml(this.htmlString, null, htmlTagHandler!!)
        if (removeTrailingNewLines) {
          htmlText = removeTrailingNewLines(htmlText)
        }

        text = htmlText
      }
    } else {
      htmlTagHandler = HtmlTagHandler(densityTextSize(context), paint, tables, 0,
          currentLayoutDirection)
      var htmlText = Html.fromHtml(this.htmlString, null, htmlTagHandler!!)
      if (removeTrailingNewLines) {
        htmlText = removeTrailingNewLines(htmlText)
      }

      text = htmlText
    }
  }

  /**
   * Set html as String to display in the textView.
   *
   * @param htmlString String of html
   * @param maxTableWidth Max width to scale table to
   */
  fun setHtmlText(htmlString: String, maxTableWidth: Int = 0) {
    this.htmlString = prepareHtmlString(htmlString)
    tables = tablesFromHtml(htmlString, paint)

    if (maxTableWidth == 0 && tables.size > 0) {
      setHtmlText(htmlString)
    } else {
      htmlTagHandler = HtmlTagHandler(densityTextSize(context), paint, tables, maxTableWidth,
          currentLayoutDirection)
      var htmlText = Html.fromHtml(this.htmlString, null, htmlTagHandler!!)
      if (removeTrailingNewLines) {
        htmlText = removeTrailingNewLines(htmlText)
      }

      text = htmlText
    }
  }

  /**
   * Set html as resource.
   *
   * * If the htmlString contains one or multiple tables, the textview
   * will wait for getting a measuring of it's width before it will
   * start displaying the string.
   *
   * You can jump over this process, by providing a maximal width.
   *
   * @param htmlResource Resource id of html
   * @see setHtmlText(Int, Int)
   */
  fun setHtmlText(htmlResource: Int) {
    val text = rawToString(htmlResource)
    if (text != null) {
      setHtmlText(text)
    }
  }

  /**
   * Set html as resource.
   *
   * @param htmlResource Resource id of html
   * @param maxTableWidth Max width to scale table to
   */
  fun setHtmlText(htmlResource: Int, maxTableWidth: Int = 0) {
    val text = rawToString(htmlResource)
    if (text != null) {
      setHtmlText(text, maxTableWidth)
    }
  }

  /**
   * Converts raw resources to String or null.
   *
   * @param Raw resource id with html
   * @return String or null
   */
  private fun rawToString(htmlResource: Int): String? {
    val inputStreamText: InputStream?

    try {
      inputStreamText = context.resources.openRawResource(htmlResource)
    } catch (exception: Resources.NotFoundException) {
      Log.e(TAG, "Provided resource not found.")
      return null
    }

    return convertStreamToString(inputStreamText)
  }

  /**
   * Converts inputStream to text.
   *
   * @param is InputStream
   */
  private fun convertStreamToString(`is`: InputStream): String {
    val s = Scanner(`is`).useDelimiter("\\A")
    return if (s.hasNext()) s.next() else ""
  }

  /**
   * Helper for removing viewTrees observer.
   * Source from: https://antonioleiva.com/kotlin-ongloballayoutlistener/
   */
  inline fun <T: View> T.afterMeasured(crossinline f: T.() -> Unit) {
    if (measuredWidth > 0) {
      f()
    }
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        if (measuredWidth > 0 && measuredHeight > 0) {
          viewTreeObserver.removeOnGlobalLayoutListener(this)
          f()
        }
      }
    })
  }

  private fun removeTrailingNewLines(spanned: Spanned): Spanned {
    var newSpanned = spanned.removeSuffix("\n")
    newSpanned = newSpanned.removeSuffix("\n")
    return newSpanned as Spanned
  }

  private fun densityTextSize(context: Context): Float {
    return textSize / context.resources.displayMetrics.density
  }

  private fun prepareHtmlString(text: String): String {
    val document = Jsoup.parse(text)
    removeComments(document)
    return replaceStyle(document)
  }

  /**
   * Replaces non supported html with textview supported htmltags and replaces
   * tags with custom tags to enable the tagHandler to handle them.
   *
   * @param text String of html
   */
  private fun replaceStyle(document: Document): String {
    val elements = document.select("*")

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

          val styleTags = p1.split(";")
          val styleMap = HashMap<String, String>()
          for (tag in styleTags) {
            val splitList = tag.split(":")
            if (splitList.size == 2) {
              styleMap.put(splitList[0].trim(), splitList[1].trim())
            }
          }

          val fontSizeString = styleMap["font-size"]

          if (fontSizeString != null) {
            val name = "<fontsize$fontSizeString></fontsize$fontSizeString>"
            element.wrap(name)
          }
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

  private fun removeComments(node: Node) {
    var i = 0
    while (i < node.childNodeSize()) {
      val child = node.childNode(i)
      if (child.nodeName().equals("#comment"))
        child.remove()
      else {
        removeComments(child)
        i++
      }
    }
  }

  fun widthWithoutPadding(): Int {
    return width - paddingLeft - paddingRight
  }

  /**
   * Returns HTMLTable ArrayList with all tables found in html.
   *
   * @param html String of html
   * @param textPaint TextPaint from TextView
   * @return ArrayList<HtmlTable> found in html string
   */
  fun tablesFromHtml(html: String, textPaint: TextPaint) : ArrayList<HtmlTable> {
    val document = Jsoup.parse(html)

    val tables: ArrayList<HtmlTable> = ArrayList()

    val tablesTags = document.select("table")
    tablesTags.mapTo(tables) { HtmlTable(it.toString(), textPaint) }

    return tables
  }
}
