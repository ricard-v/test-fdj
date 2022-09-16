package com.mackosoft.core.base.model

import com.mackosoft.core.base.network.BaseRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

abstract class BaseModel<RemoteDataSource : BaseRemoteDataSource> @Inject constructor(
    protected val remoteDataSource: RemoteDataSource,
    protected val ioDispatcher: CoroutineDispatcher,
)