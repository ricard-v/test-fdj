package com.mackosoft.features.homepage.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mackosoft.core.base.presenter.BasePresenterFragment
import com.mackosoft.features.homepage.HomePageContract
import com.mackosoft.features.homepage.R
import com.mackosoft.features.homepage.databinding.FragmentHomePageBinding
import com.mackosoft.features.homepage.model.entities.FootballTeamEntity
import com.mackosoft.features.homepage.presenter.HomePagePresenter
import com.mackosoft.features.homepage.view.adapter.FootballTeamsListAdapter
import com.mackosoft.features.homepage.view.adapter.FootballTeamsListDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : BasePresenterFragment<HomePagePresenter>(R.layout.fragment_home_page),
    HomePageContract.View {

    companion object {
        private const val FOOTBALL_TEAM_DETAILS_REQUEST_KEY = "football_team_details_request_key"
        private const val RESULT_KEY_FOOTBALL_TEAMS_DETAILS_ID =
            "result_arg_football_team_details_id"
        private const val RESULT_KEY_FOOTBALL_TEAMS_DETAILS_NAME =
            "result_arg_football_team_details_name"

        fun setResultListener(
            fragmentManager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            action: (teamId: String?, teamName: String?) ->Unit
        ) {
            fragmentManager.setFragmentResultListener(
                FOOTBALL_TEAM_DETAILS_REQUEST_KEY,
                lifecycleOwner
            ) { _, bundle ->
                val id = bundle.getString(RESULT_KEY_FOOTBALL_TEAMS_DETAILS_ID)
                val name = bundle.getString(RESULT_KEY_FOOTBALL_TEAMS_DETAILS_NAME)
                action.invoke(id, name)
            }
        }
    }

    @Inject
    override lateinit var presenter: HomePagePresenter
    private val binding: FragmentHomePageBinding by viewBinding()
    private var searchView: SearchView? = null

    private val footballTeamsListAdapter: FootballTeamsListAdapter = FootballTeamsListAdapter(
        onItemClickedListener = ::onFootballTeamItemClicked
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onResume() {
        super.onResume()
        searchView?.query?.toString()?.let(presenter::searchChampionship)
    }

    private fun setupUi() {
        setupMenu()
        setupTeamsList()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.home_page_fragment_menu, menu)
                    (menu.findItem(R.id.app_bar_search).actionView as? SearchView)?.apply {
                        searchView = this
                        queryHint = getText(R.string.search_view_hint_text)
                        setOnQueryTextListener(
                            object : SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean = false

                                override fun onQueryTextChange(newText: String?): Boolean {
                                    newText?.let(presenter::searchChampionship)
                                    return false
                                }
                            }
                        )
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
            }
        )
    }

    private fun setupTeamsList() {
        binding.teams.apply {
            setHasFixedSize(true)
            adapter = footballTeamsListAdapter
            addItemDecoration(FootballTeamsListDecoration(requireContext()))
        }
    }

    private fun onFootballTeamItemClicked(footballTeamEntity: FootballTeamEntity) {
        setFragmentResult(
            requestKey = FOOTBALL_TEAM_DETAILS_REQUEST_KEY,
            result = bundleOf(
                RESULT_KEY_FOOTBALL_TEAMS_DETAILS_ID to footballTeamEntity.id,
                RESULT_KEY_FOOTBALL_TEAMS_DETAILS_NAME to footballTeamEntity.teamName,
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView = null
    }

    // region Contract
    override fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.loader.show()
        else
            binding.loader.hide()
    }

    override fun showSearchResults(results: List<FootballTeamEntity>) {
        footballTeamsListAdapter.submitList(results)
    }

    override fun showNoResultsFound(show: Boolean, searchKey: String) {
        binding.noSearchResultsLabel.text = getString(R.string.no_search_results_for, searchKey)
        binding.noSearchResultsLabel.isVisible = show
    }

    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }
    // endregion Contract
}