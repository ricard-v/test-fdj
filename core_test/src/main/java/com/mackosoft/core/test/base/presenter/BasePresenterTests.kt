package com.mackosoft.core.test.base.presenter

import androidx.annotation.CallSuper
import com.mackosoft.core.base.model.BaseModel
import com.mackosoft.core.base.presenter.BaseCoroutinePresenter
import com.mackosoft.core.test.base.BaseTests
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BasePresenterTests<Model : BaseModel<*>, View, Presenter : BaseCoroutinePresenter<Model, View>> :
    BaseTests() {

    protected abstract val mockedModel: Model
    protected abstract val mockedView: View
    protected abstract val presenter: Presenter

    @Before
    @CallSuper
    open fun setupTest() {
        Dispatchers.setMain(
            StandardTestDispatcher(
                scheduler = testScope.testScheduler,
                name = "UI Thread",
            )
        )
    }

    @After
    @CallSuper
    override fun tearDownTest() {
        super.tearDownTest()
        Dispatchers.resetMain()
    }
}