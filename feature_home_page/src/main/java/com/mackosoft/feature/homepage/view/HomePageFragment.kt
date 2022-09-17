package com.mackosoft.feature.homepage.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mackosoft.core.base.presenter.BasePresenterFragment
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.R
import com.mackosoft.feature.homepage.databinding.FragmentHomePageBinding
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import com.mackosoft.feature.homepage.presenter.HomePagePresenter
import com.mackosoft.feature.homepage.view.adapter.FootballTeamsListAdapter
import com.mackosoft.feature.homepage.view.adapter.FootballTeamsListDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : BasePresenterFragment<HomePagePresenter>(R.layout.fragment_home_page),
    HomePageContract.View {

    @Inject
    override lateinit var presenter: HomePagePresenter
    private val binding: FragmentHomePageBinding by viewBinding()
    private var searchView: SearchView? = null

    private val footballTeamsListAdapter: FootballTeamsListAdapter = FootballTeamsListAdapter()

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
                    (menu.findItem(R.id.app_bar_search).actionView as SearchView)?.let {
                        searchView = it
                        it.setOnQueryTextListener(
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

    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }
    // endregion Contract
}