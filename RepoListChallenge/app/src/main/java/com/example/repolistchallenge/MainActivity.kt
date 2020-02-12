package com.example.repolistchallenge

import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.fragment.NavHostFragment
import com.example.repolistchallenge.core.BaseActivity

class MainActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val host = NavHostFragment.create(R.navigation.nav_host)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, host).setPrimaryNavigationFragment(host).commit()
    }

}
