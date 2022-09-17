package com.mackosoft.core.test.base.model

import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import com.mackosoft.core.base.model.BaseModel
import com.mackosoft.core.test.base.BaseTests
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