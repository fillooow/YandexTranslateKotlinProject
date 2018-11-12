package com.bignerdranch.android.yandexkotlinproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity() {

    //private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        //toolbar = toolbar_main
        //setSupportActionBar(toolbar_main)
        loadFragment(TranslateFragment(), false)
        bottom_navigation_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //toolbar_main.title = resources.getString(R.string.translate_icon)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->  when(item.itemId){
            R.id.translate_navigation -> {
                //toolbar_main.title = resources.getString(R.string.translate_icon)
                loadFragment(TranslateFragment(), false)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.bookmarks_history_navigation ->  {
                //toolbar_main.title = resources.getString(R.string.bookmarks_history_navigation)
                loadFragment(BookmarksHistoryFragment(), false)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.settings_navigation -> {
                //toolbar_main.title = resources.getString(R.string.settings)
                loadFragment(SettingsFragment(), false)
                return@OnNavigationItemSelectedListener  true
            }
    }
        Log.d("error", "onNavigationItemSelectedListener упал")
        return@OnNavigationItemSelectedListener  false
    }

    private fun loadFragment(fragment: Fragment, isBackStack: Boolean){
        val fragmentManager = supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container_main, fragment)
        when (isBackStack){
            true -> fragmentManager.addToBackStack(null).commit()
            false -> fragmentManager.commit()
        }
    }
}
