# HtmlTextView

[ ![Download](https://api.bintray.com/packages/xamoom/maven/htmltextview/images/download.svg) ](https://bintray.com/xamoom/maven/htmltextview/_latestVersion)

Custom TextView with increased abilities to display html.

It extends the textViews ability to also display:
* inline styles (font-weight: bold, text-decoration: underline, font-style: italic, font-size)
* nested ol
* nested ul
* tables

# Install

```
compile 'com.xamoom.android:htmltextview:1.0.0'
```


# Usage

Add the HtmlTextView to your Layout files (or do it in code).

```xml
<com.android.xamoom.htmltextview.HtmlTextView
            android:id="@+id/html_text_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
```

Find your view and set your text.

Java
```java
htmlTextView = (HtmlTextView) findViewById(R.id.html_text_view)

// with string
htmlTextView.setHtmlText("<b>Bold Text</b>");
// with resource
htmlTextView.setHtmlText(R.raw.test);
// with a maximal size of displayed tables
htmlTextView.setHtmlText(R.raw.test, 100);
```

Kotlin
```kotlin
htmlTextView = findViewById(R.id.html_text_view) as HtmlTextView

// with string
htmlTextView.setHtmlText("<b>Bold Text</b>")
// with resource
htmlTextView.setHtmlText(R.raw.test)
// with a maximal size of displayed tables
htmlTextView.setHtmlText(R.raw.test, 100)
```

# Contribution

If you want to contribute, please check the issues.
Fork the project, create a branch for the issue/improvement, make your changes and create a pull request.