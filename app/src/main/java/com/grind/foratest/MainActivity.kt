package com.grind.foratest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grind.foratest.fragments.SearchFragment

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, SearchFragment())
            .commit()
    }
}
