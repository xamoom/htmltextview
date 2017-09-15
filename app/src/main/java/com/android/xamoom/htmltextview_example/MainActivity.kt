package com.android.xamoom.htmltextview_example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.xamoom.htmltextview.HtmlTextView
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {
  lateinit var htmlTextView: HtmlTextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    htmlTextView = findViewById(R.id.html_text_view) as HtmlTextView

    htmlTextView.setHtmlText(R.raw.dirty)
  }
}