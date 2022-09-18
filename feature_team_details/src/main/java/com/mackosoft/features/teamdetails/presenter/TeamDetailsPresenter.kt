package com.mackosoft.features.teamdetails.presenter

import com.mackosoft.core.base.presenter.BaseCoroutinePresenter
import com.mackosoft.core.dispatchers.MainDispatcher
import com.mackosoft.features.teamdetails.TeamDetailsContract
import com.mackosoft.features.teamdetails.model.TeamDetailsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class TeamDetailsPresenter @Inject constructor(
    view: TeamDetailsContract.View,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    teamDetailsModel: TeamDetailsModel,
) : BaseCoroutinePresenter<TeamDetailsModel, TeamDetailsContract.View>(
    mainDispatcher,
    teamDetailsModel,
    WeakReference(view),
), TeamDetailsContract.Presenter {

    override fun getFootballTeamDetails(teamId: String) {
        launch {
            view.get()?.showLoading(isLoading = true)
            val result = model.getFootballTeamDetails(id = teamId)
            result.fold(
                onSuccess = { details ->
                    view.get()?.showLoading(isLoading = false)
                    view.get()?.showTeamDetails(teamDetailsEntity = details)
                },
                onFailure = { error ->
                    view.get()?.showLoading(isLoading = false)
                    view.get()?.showErrorMessage(
                        errorMessage = error.localizedMessage ?: "unknown error",
                    )
                }
            )
        }
    }
}