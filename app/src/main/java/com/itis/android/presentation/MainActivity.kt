package com.itis.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.android.R
import com.itis.android.presentation.search.SearchFragment

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
