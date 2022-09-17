package com.mackosoft.feature.homepage.presenter

import com.mackosoft.core.base.presenter.BaseCoroutinePresenter
import com.mackosoft.core.dispatchers.MainDispatcher
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.model.HomePageModel
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import javax.inject.Inject

class HomePagePresenter @Inject constructor(
    view: HomePageContract.View,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    homePageModel: HomePageModel,
) : BaseCoroutinePresenter<HomePageModel, HomePageContract.View>(
    mainDispatcher,
    homePageModel,
    WeakReference(view),
), HomePageContract.Presenter {

    private companion object {
        const val DEBOUNCE_THRESHOLD_MS = 300L
    }

    private var currentSearchJob: Job? = null

    override fun searchChampionship(searchKey: String) {
        currentSearchJob?.cancel()
        currentSearchJob = launch {
            delay(DEBOUNCE_THRESHOLD_MS)
            view.get()?.showLoading(isLoading = true)
            view.get()?.showNoResultsFound(show = false, searchKey = "")

            val result = model.getFootballTeamsFromChampionShip(championShip = searchKey)
            result.fold(
                onSuccess = { teams ->
                    view.get()?.showLoading(isLoading = false)
                    view.get()?.showSearchResults(results = teams)
                    if (teams.isEmpty()) {
                        view.get()?.showNoResultsFound(show = true, searchKey = searchKey)
                    } else {
                        view.get()?.showNoResultsFound(show = false, searchKey = "")
                    }
                },
                onFailure = { error ->
                    view.get()?.showLoading(isLoading = false)
                    view.get()?.showSearchResults(results = emptyList())
                    view.get()?.showErrorMessage(
                        errorMessage = error.localizedMessage ?: "unknown error",
                    )
                }
            )
        }
    }
}