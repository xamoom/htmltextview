package com.android.xamoom.htmltextview_example


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.xamoom.htmltextview.HtmlTextView


/**
 * A simple [Fragment] subclass.
 */
class ScrollFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    val view = inflater!!.inflate(R.layout.fragment_scroll, container, false)
    val htmlTextView = view.findViewById<HtmlTextView>(R.id.html_text_view)
    htmlTextView.setHtmlText(R.raw.test)
    return view
  }


  companion object {

    fun newInstance(): ScrollFragment {
      val fragment = ScrollFragment()
      return fragment
    }
  }

}// Required empty public constructor
