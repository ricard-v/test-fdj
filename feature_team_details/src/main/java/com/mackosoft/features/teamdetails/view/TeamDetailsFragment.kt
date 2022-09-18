package com.mackosoft.features.teamdetails.view

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
    private val teamNAme: String?
        get() = arguments?.getString(ARG_KEY_TEAM_NAME)
}