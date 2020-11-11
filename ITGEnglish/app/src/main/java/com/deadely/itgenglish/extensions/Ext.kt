package com.deadely.itgenglish.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setActivityTitle(@StringRes id: Int) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = getString(id)
}

fun Fragment.setActivityTitle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}


