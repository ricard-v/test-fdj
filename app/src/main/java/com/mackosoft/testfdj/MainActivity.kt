package com.mackosoft.testfdj

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.mackosoft.core.extensions.takeIfNotNull
import com.mackosoft.features.homepage.view.HomePageFragment
import com.mackosoft.features.teamdetails.view.TeamDetailsFragment
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
                    findNavController(R.id.nav_host).navigate(
                        R.id.show_team_details,
                        bundleOf(
                            TeamDetailsFragment.ARG_KEY_TEAM_ID to teamId,
                            TeamDetailsFragment.ARG_KEY_TEAM_NAME to teamName,
                        ),
                    )
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