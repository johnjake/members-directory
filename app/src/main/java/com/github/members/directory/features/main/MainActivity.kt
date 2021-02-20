package com.github.members.directory.features.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.R
import com.github.members.directory.features.history.HistoryFragment
import com.github.members.directory.features.users.UsersFragment
import com.github.members.directory.features.visited.VisitedFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    companion object {
        var onBackPress: Boolean = false
        var onDetailsFragment: Boolean = false
        var onVisitedFragment: Boolean = false
        var onSearchFragment: Boolean = false

        const val USER_FRAGMENT = "user_fragment"
        const val REPOSITORY_FRAGMENT = "repository_fragment"
        const val VISITED_FRAGMENT = "visited_fragment"
    }

    private lateinit var bottomNavView: BottomNavigationView
    private var bundle = Bundle()

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializedUI()
        getAccessTokenFromDevice()
    }

    private fun getAccessTokenFromDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.d("Fetching FCM registration token failed $task.exception")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val accessToken = task.result

            // Log and toast

            Timber.e(accessToken)
        })
    }

    private fun initFirebase() {
        val options = FirebaseOptions.Builder()
            .setApplicationId("APP ID") // Required for Analytics.
            .setProjectId("PROJECT ID") // Required for Firebase Installations.
            .setApiKey("GOOGLE API KEY") // Required for Auth.
            .build()
        FirebaseApp.initializeApp(this, options, "instafood")
    }

    private fun initializedUI() {
        setupBottomNavMenu()
    }

    private fun setupBottomNavMenu(){
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav).apply {
            itemIconTintList = null
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.destination_movies -> {
                        openFragment(UsersFragment.newInstance(USER_FRAGMENT), USER_FRAGMENT)
                        return true
                    }
                    R.id.destination_discover -> {
                        val instanceDiscover = HistoryFragment.newInstance(REPOSITORY_FRAGMENT)
                        instanceDiscover.arguments = bundle
                        openFragment(instanceDiscover, REPOSITORY_FRAGMENT)
                        return true
                    }
                    R.id.destination_visited -> {
                        openFragment(
                            VisitedFragment.newInstance(VISITED_FRAGMENT),
                            VISITED_FRAGMENT
                        )
                        return true
                    }
                }
                return true
            }
        })
    }

    fun openFragment(fragment: Fragment, fragmentName: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}