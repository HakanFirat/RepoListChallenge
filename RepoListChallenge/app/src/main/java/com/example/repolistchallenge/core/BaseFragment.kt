package com.example.repolistchallenge.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.repolistchallenge.extensions.hideKeyboard
import java.util.*

abstract class BaseFragment: Fragment() {

    @get:LayoutRes
    protected abstract val layoutResource: Int
    val baseActivity get() = activity as BaseActivity

    private var navigateBoolean: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResource, container, false)
        view?.hideKeyboard()
        setToolbarVisibility(true)

        return view
    }

    open fun onArguments(arguments: Bundle) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let(::onArguments)
        initViewsOnViewCreated(view)
    }

    open fun initViewsOnViewCreated(view: View) {}

    fun navigateTo(navigateTo: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
        if (!navigateBoolean)
            return

        navigateBoolean = false
        Timer().schedule(object : TimerTask() {
            override fun run() {
                navigateBoolean = true
            }
        }, 500)

        findNavController().navigate(navigateTo, bundle, navOptions)
    }

    var toolbarTitle: String = ""
        get() = baseActivity.toolbarTitle
        set(value) {
            baseActivity.toolbarTitle = value
            field = value
        }

    fun hideToolbar() {
        baseActivity.toolbarVisibility = View.GONE
    }

    fun showToolbar() {
        baseActivity.toolbarVisibility = View.VISIBLE
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        baseActivity.toolbarVisibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun hideBackButton(){
        baseActivity.backButtonVisibility = View.GONE
    }

    fun showBackButton(){
        baseActivity.backButtonVisibility = View.VISIBLE
    }
}