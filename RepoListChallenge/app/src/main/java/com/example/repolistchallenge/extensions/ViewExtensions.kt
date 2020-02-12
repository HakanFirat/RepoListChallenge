package com.example.repolistchallenge.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    if (!this.isFocused)
        this.requestFocus()
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
    this.clearFocus()
}