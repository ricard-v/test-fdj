package com.mackosoft.core.base.model

import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

abstract class BaseModel<RemoteDataSource : BaseRemoteDataSource>(
    protected val remoteDataSource: RemoteDataSource,
    protected val defaultDispatcher: CoroutineDispatcher,
    protected val ioDispatcher: CoroutineDispatcher,
)