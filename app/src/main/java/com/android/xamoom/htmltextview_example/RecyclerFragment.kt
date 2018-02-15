package com.android.xamoom.htmltextview_example

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecyclerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecyclerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerFragment : Fragment() {

  lateinit var recyclerView : RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    val view = inflater!!.inflate(R.layout.fragment_recycler, container, false)
    recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

    setupRecyclerView()

    return view
  }

  companion object {

    fun newInstance(): RecyclerFragment {
      val fragment = RecyclerFragment()
      return fragment
    }
  }

  fun setupRecyclerView() {
    recyclerView.layoutManager =LinearLayoutManager(context)
    recyclerView.adapter = TextAdapter()
  }

}// Required empty public constructor
