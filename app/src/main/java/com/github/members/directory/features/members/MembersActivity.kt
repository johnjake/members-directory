package com.github.members.directory.features.members

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.members.directory.R
import kotlinx.android.synthetic.main.activity_main.*

class MembersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var isDark = getThemeStatePref()
        if (isDark) {
            // dark theme is on
            search_input.setBackgroundResource(R.drawable.search_input_dark_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        } else {
            // light theme is on
            search_input.setBackgroundResource(R.drawable.search_input_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }

        fab_switcher.setOnClickListener {
            isDark = !isDark
            if (isDark) {
                search_input.setBackgroundResource(R.drawable.search_input_dark_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            } else {
                search_input.setBackgroundResource(R.drawable.search_input_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }
        }


    }

    private fun saveThemeStatePref(isDark: Boolean) {
        val pref = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(DARK_MODE, isDark)
        editor.apply()
    }

    private fun getThemeStatePref(): Boolean {
        val pref = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        return pref.getBoolean(DARK_MODE, false)
    }

    companion object {
        const val DARK_MODE = "isDark"
        const val SHARED_PREF = "myPref"
    }
}