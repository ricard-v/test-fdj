package com.mackosoft.core.base.model

import com.mackosoft.core.base.BaseTests
import com.mackosoft.core.base.network.BaseRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseModelTests<RemoteDataSource : BaseRemoteDataSource, Model : BaseModel<*>> :
    BaseTests() {

    protected abstract val mockedRemoteDatasource: RemoteDataSource
    protected abstract val mockedModel: Model

    /**
     * To be used when instantiating the [mockedModel] which
     * requires an IO [CoroutineDispatcher].
     */
    protected val ioDispatcher = StandardTestDispatcher(
        scheduler = testScope.testScheduler,
        name = "IO Dispatcher",
    )
}