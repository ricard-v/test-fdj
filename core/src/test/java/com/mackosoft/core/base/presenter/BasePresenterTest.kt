package com.mackosoft.core.base.presenter

import androidx.annotation.CallSuper
import com.mackosoft.core.base.BaseTests
import com.mackosoft.core.base.model.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BasePresenterTest<Model : BaseModel<*>, View, Presenter : BaseCoroutinePresenter> :
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