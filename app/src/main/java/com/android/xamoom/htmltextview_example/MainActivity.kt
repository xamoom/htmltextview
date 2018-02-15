package com.android.xamoom.htmltextview_example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.android.xamoom.htmltextview.HtmlTextView
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {
  lateinit var htmlTextView: HtmlTextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_frame_layout, ScrollFragment.newInstance())
        .commit()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_scroll -> {
      supportFragmentManager
          .beginTransaction()
          .replace(R.id.main_frame_layout, ScrollFragment.newInstance())
          .commit()
      true
    }

    R.id.action_recycler -> {
      supportFragmentManager
          .beginTransaction()
          .replace(R.id.main_frame_layout, RecyclerFragment.newInstance())
          .commit()
      true
    }

    else -> {
      // If we got here, the user's action was not recognized.
      // Invoke the superclass to handle it.
      super.onOptionsItemSelected(item)
    }
  }


}