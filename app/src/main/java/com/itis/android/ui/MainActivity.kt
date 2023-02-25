package com.itis.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.android.R
import com.itis.android.ui.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SearchFragment())
            .commit()
    }
}
