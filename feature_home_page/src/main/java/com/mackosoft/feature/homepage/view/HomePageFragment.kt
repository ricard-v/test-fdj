package com.mackosoft.feature.homepage.view

import android.widget.Toast
import com.mackosoft.core.base.presenter.BasePresenterFragment
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.R
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import com.mackosoft.feature.homepage.presenter.HomePagePresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : BasePresenterFragment<HomePagePresenter>(R.layout.fragment_home_page), HomePageContract.View {

    @Inject
    override lateinit var presenter: HomePagePresenter

    override fun onResume() {
        super.onResume()
        presenter.searchChampionship("Ligue 1") // TODO
    }

    // region Contract
    override fun showSearchResults(results: List<FootballTeamEntity>) {
        // TODO
        Toast.makeText(requireContext(), "got ${results.size} teams!", Toast.LENGTH_LONG).show()
    }

    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }
    // endregion Contract
}