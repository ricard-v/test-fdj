package com.mackosoft.features.teamdetails.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mackosoft.features.teamdetails.R
import com.mackosoft.features.teamdetails.databinding.FragmentTeamDetailsBinding

class TeamDetailsFragment : Fragment(R.layout.fragment_team_details) {

    companion object {
        const val ARG_KEY_TEAM_ID = "arg_key_team_id"
        const val ARG_KEY_TEAM_NAME = "arg_key_team_name"
    }

    private val binding: FragmentTeamDetailsBinding by viewBinding()

    private val teamId: String?
        get() = arguments?.getString(ARG_KEY_TEAM_ID)
    private val teamName: String?
        get() = arguments?.getString(ARG_KEY_TEAM_NAME)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setupMenu()
        updateBarTitle()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menu.clear()
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
            }
        )
    }

    private fun updateBarTitle() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = teamName
    }
}