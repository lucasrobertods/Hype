package br.com.hype.presenter.base.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(manager: FragmentManager?) {
    manager?.let { show(manager, this.javaClass.simpleName) }
}

fun Fragment.supportFragmentManager(execute: FragmentManager.() -> Unit) {
    val supportFragmentManager = activity?.supportFragmentManager
        ?: throw IllegalArgumentException("Activity null")
    execute(supportFragmentManager)
}