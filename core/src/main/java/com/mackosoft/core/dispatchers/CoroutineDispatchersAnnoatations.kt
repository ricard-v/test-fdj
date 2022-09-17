package com.mackosoft.core.dispatchers

import javax.inject.Qualifier

// taken from https://www.valueof.io/blog/injecting-coroutines-dispatchers-with-dagger
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher