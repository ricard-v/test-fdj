package com.mackosoft.testfdj

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.mackosoft.core.extensions.takeIfNotNull
import com.mackosoft.features.homepage.view.HomePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navHostFragment: NavHostFragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host) as? NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment?.childFragmentManager?.let { fragmentManager ->
            HomePageFragment.setResultListener(
                fragmentManager = fragmentManager,
                lifecycleOwner = this
            ) { teamId: String?, teamName: String? ->
                takeIfNotNull(teamId, teamName)?.let { (id, name) ->
                    Toast.makeText(this, "show details for $name", Toast.LENGTH_LONG).show()
                } ?: run {
                    Toast.makeText(
                        this,
                        R.string.missing_data_for_teams_details_request,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}