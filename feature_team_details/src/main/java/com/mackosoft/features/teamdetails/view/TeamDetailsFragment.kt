package com.mackosoft.features.teamdetails.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mackosoft.core.base.presenter.BasePresenterFragment
import com.mackosoft.core.image.GlideApp
import com.mackosoft.features.teamdetails.R
import com.mackosoft.features.teamdetails.TeamDetailsContract
import com.mackosoft.features.teamdetails.databinding.FragmentTeamDetailsBinding
import com.mackosoft.features.teamdetails.model.entities.FootballTeamDetailsEntity
import com.mackosoft.features.teamdetails.presenter.TeamDetailsPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamDetailsFragment :
    BasePresenterFragment<TeamDetailsPresenter>(R.layout.fragment_team_details),
    TeamDetailsContract.View {

    companion object {
        const val ARG_KEY_TEAM_ID = "arg_key_team_id"
        const val ARG_KEY_TEAM_NAME = "arg_key_team_name"
    }

    @Inject
    override lateinit var presenter: TeamDetailsPresenter
    private val binding: FragmentTeamDetailsBinding by viewBinding()

    private val teamId: String?
        get() = arguments?.getString(ARG_KEY_TEAM_ID)
    private val teamName: String?
        get() = arguments?.getString(ARG_KEY_TEAM_NAME)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onResume() {
        super.onResume()
        teamId?.let(presenter::getFootballTeamDetails) ?: run {
            Toast.makeText(
                requireContext(),
                R.string.missing_team_id_in_arguments,
                Toast.LENGTH_LONG,
            ).show()
            findNavController().popBackStack()
        }
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

    // region Contract
    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loader.show()
            binding.detailsGroup.isVisible = false
        } else {
            binding.loader.hide()
            binding.detailsGroup.isVisible = true
        }
    }

    override fun showTeamDetails(teamDetailsEntity: FootballTeamDetailsEntity) {
        teamDetailsEntity.bannerUrl?.let { url ->
            GlideApp.with(binding.banner).load(url).into(binding.banner)
        } ?: run {
            binding.banner.isVisible = false
        }
        binding.country.text = teamDetailsEntity.country
        binding.championship.text = teamDetailsEntity.championShipName
        binding.description.text = teamDetailsEntity.description
    }

    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }
    // endregion Contract
}