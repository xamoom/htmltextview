package com.android.xamoom.htmltextview_example

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.xamoom.htmltextview.HtmlTextView

/**
 * Created by raphaelseher on 15.02.18.
 */

class TextAdapter : RecyclerView.Adapter<TextAdapter.TextViewHolder>()   {
  val itemSize = 5

  override fun getItemCount(): Int {
    return itemSize
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TextViewHolder {
    val inflatedView = parent!!.inflate(R.layout.viewholder_text, false)
    return TextViewHolder(inflatedView)
  }

  override fun onBindViewHolder(holder: TextViewHolder?, position: Int) {
    holder!!.htmlTextView.setHtmlText(R.raw.test)
  }


  class TextViewHolder: RecyclerView.ViewHolder {
    lateinit var htmlTextView: HtmlTextView

    constructor(itemView: View?) : super(itemView) {
      htmlTextView = itemView!!.findViewById(R.id.html_text_view)
    }
  }

}