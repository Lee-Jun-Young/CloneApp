package com.example.cloneapp.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneapp.R
import com.example.cloneapp.ui.favorite.FavoriteFragment
import com.example.cloneapp.ui.search.SearchFragment
import com.example.cloneapp.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.linearLayout, HomeFragment()).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.menu_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, SearchFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.menu_favorite -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, FavoriteFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.menu_user -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, UserFragment()).commitAllowingStateLoss()
                return true
            }
        }
        return false
    }
}