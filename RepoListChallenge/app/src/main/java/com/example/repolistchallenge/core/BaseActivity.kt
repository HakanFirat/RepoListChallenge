package com.example.repolistchallenge.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.repolistchallenge.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initToolbar()
    }

    private fun initToolbar() {
        if (mToolbar != null)
            setSupportActionBar(mToolbar)

        iv_page_back.setOnClickListener {
            onBackPressed()
        }
    }

    var backButtonVisibility: Int
        get() = iv_page_back?.visibility ?: View.GONE
        set(value) {
            iv_page_back?.visibility = value
        }

    var toolbarTitle: String = ""
        get() = tv_app_title?.text.toString()
        set(value) {
            tv_app_title?.text = value
            field = value
        }

    var toolbarVisibility: Int = View.VISIBLE
        get() = mToolbar?.visibility ?: View.GONE
        set(value) {
            mToolbar?.visibility = value
            field = value
        }

    override fun onBackPressed() {
        window.decorView.hideKeyboard()
        super.onBackPressed()
    }


}